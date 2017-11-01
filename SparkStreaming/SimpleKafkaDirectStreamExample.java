package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import kafka.serializer.StringDecoder;


public class test {
	public static void main(String[] args) throws InterruptedException {
		SparkConf conf=new SparkConf().setAppName("test").setMaster("local[2]");
		JavaStreamingContext jsc=new JavaStreamingContext(conf, Durations.seconds(10));

		Set<String> topicSet = new HashSet<String>(Arrays.asList("test"));
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", "localhost:9092");
		JavaPairInputDStream<String, String> data= KafkaUtils.createDirectStream(jsc, String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topicSet);
		data.print();
		jsc.start();
		jsc.awaitTermination();

	}
}
