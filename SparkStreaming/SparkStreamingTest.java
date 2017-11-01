package com.ril.jio.spark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import kafka.serializer.StringDecoder;


public class SparkStreamingTest {
	public static void main(String[] args) throws InterruptedException {
		SparkConf conf = new SparkConf().setAppName("Test").setMaster("local");
		JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(10));
		
		HashSet<String> topicSet = new HashSet<String>(Arrays.asList("test"));
		HashMap<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", "localhost:9092");
		kafkaParams.put("auto.offset.reset", "smallest");
		kafkaParams.put("group.id", "test-group");
		kafkaParams.put("zookeeper.connect", "localhost:2181");
		
		JavaPairDStream<String, String> messages = KafkaUtils.createDirectStream(
				jsc, String.class, String.class, 
				StringDecoder.class, StringDecoder.class, kafkaParams, topicSet);
 		
		messages.print();
		jsc.start();
		jsc.awaitTermination();
	}
}
