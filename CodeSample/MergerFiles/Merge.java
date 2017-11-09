package org.ril.jio.spark;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Merge {

    public static FileSystem fs=null;
    public static Configuration hdconf=null;
    static{
        hdconf=new Configuration();
        try {
            fs=FileSystem.get(hdconf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
    	long currTimestamp = System.currentTimeMillis();
    	String fileName = "FlumeData."+currTimestamp;
//      SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark-Merger").set("spark.executor.memory", "2g");
        SparkConf conf = new SparkConf().setMaster("yarn-cluster").setAppName("Spark-Merger-"+currTimestamp);
        JavaSparkContext sc=new JavaSparkContext(conf);
        Configuration newConf=new Configuration(hdconf);
        JobConf jobConf=new JobConf();
        newConf.set("mapreduce.output.basename", fileName);
        newConf.set("mapreduce.output.fileoutputformat.compress", "true");
        newConf.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.GzipCodec");
//        newConf.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.BZip2Codec");
        newConf.set("mapreduce.output.fileoutputformat.compress.type", "BLOCK");
        newConf.set("mapreduce.output.fileoutputformat.outputdir", args[1]);//"hdfs://localhost:9000/test1Merge"
        jobConf.setCompressMapOutput(true);
        
        JavaPairRDD<String,String> listFiles=sc.wholeTextFiles(args[0]);
        if(fs.exists(new Path(args[1]))){
        	fs.delete(new Path(args[1]));
         	listFiles.saveAsNewAPIHadoopDataset(newConf);
        }
        else{
        	listFiles.saveAsNewAPIHadoopDataset(newConf);
     } 

        Path output = new Path(args[1]); // Temp outpath
        if(fs.exists(output)){
                FileStatus[]  status=fs.listStatus(output);
                for(int i=0;i<status.length;i++){
                	if(!status[i].getPath().toString().contains("_SUCCESS"))
                	{
                		System.out.println("Files in Zip Folder ============= : "+status[i].getPath().toString());
                        fs.rename(status[i].getPath(), new Path(args[2]));
                	}
                }
        }
    }
}

