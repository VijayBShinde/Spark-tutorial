package com.ril.example.bulkdataload;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 
 * @author hadoop
 *
 *	Usage $yarn jar MRBulkLoad2.jar com.ril.example.bulkdataload.HBaseBulkLoad /vijay/sample.json /vijay/MRBulkLoad emptest
 *   yarn jar MRBulkLoad2.jar com.ril.example.bulkdataload.HBaseBulkLoad hdfs://localhost:9000/data/sample.csv hdfs://localhost:9000/data/BulkLoad/Out emptest
 */
public class HBaseBulkLoad extends Configured implements Tool{

	public static class BulkLoadMap extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put>{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			String line = value.toString();
			String[] parts =  line.split(",");
			String rowKey = parts[0];
			
			ImmutableBytesWritable HKey = new ImmutableBytesWritable(Bytes.toBytes(rowKey));
			Put HPut = new Put(Bytes.toBytes(rowKey));
			
			HPut.add(Bytes.toBytes("r"), Bytes.toBytes("name"), Bytes.toBytes(parts[1]));
			HPut.add(Bytes.toBytes("r"), Bytes.toBytes("mail_id"), Bytes.toBytes(parts[2]));
			HPut.add(Bytes.toBytes("r"), Bytes.toBytes("sal"), Bytes.toBytes(parts[3]));
			context.write(HKey,HPut);

		}
	}
	@SuppressWarnings("deprecation")
	public int run(String[] args) throws Exception,IllegalArgumentException {
		
		if (args.length != 3) {
			System.err.printf("Usage: %s [generic options] <input> <output> <table>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		Configuration conf = new Configuration();
		conf.addResource(new Path("/usr/local/hadoop/hadoop-2.7.2/etc/hadoop/core-site.xml"));
		conf.addResource(new Path("/usr/local/hadoop/hadoop-2.7.2/etc/hadoop/hdfs-site.xml"));
		
		String inputPath = args[0];
		HTable table = new HTable(conf, args[2]);
		conf.set("hbase.mapred.outputtable", args[2]);
		
		Job job = new Job(conf, "************** HBase Bulk Data loading ******************");
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Put.class);
		job.setSpeculativeExecution(false);
		job.setReduceSpeculativeExecution(false);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(HFileOutputFormat.class);
		job.setJarByClass(HBaseBulkLoad.class);
		job.setMapperClass(HBaseBulkLoad.BulkLoadMap.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputPath));
		TextOutputFormat.setOutputPath(job, new Path(args[1]));
		// System.out.println("here" + job + " : " + table);
		// Actual issue is here only  rest of thing are correct. Need to check this issue
		// Exception in thread "main" java.lang.IllegalArgumentException: Can not create a Path from a null string
		HFileOutputFormat.configureIncrementalLoad(job, table);
		System.out.println("here2");
		int returnValue = job.waitForCompletion(true) ? 1 : 0;
		return returnValue;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Args : " + args[0] + " : " + args[1] + " : " + args[2]);
		int exitCode = ToolRunner.run(new HBaseBulkLoad(), args);
		System.exit(exitCode);
	}
}
