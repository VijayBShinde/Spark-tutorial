package com.ril.jio.kafka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class MyKafkaProducer {
	public static void main(String[] args) throws InterruptedException {
		/*if(args.length == 0){
			System.out.println("Enter topic name");
			return;
		}*/
		
		String topic = "test";//args[0].toString();
		System.out.println("Topic: " + topic);
		
		// Check zookeeper running status
		
		// Create instance for Properties to access producer config
		Properties prop = new Properties();
		
		// Assign localhost ID
		prop.put("bootstrap.servers", "localhost:9092");
		
		// Set ack request for producer request
		prop.put("acks", "all");
		
		//If the request fails, the producer can automatically retry
		prop.put("retries", 0);
		
		// Specify the buffer size in config
		prop.put("batch.size", 16384);
		
		// Reduce the no of request less than 0
		prop.put("linger.ms", 1);
		
		// The buffer.memory controls the total amount of memory to the producer for buffering
		prop.put("buffer.memory", 33554432);
		
		prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		
		Producer<String, String> producer = new KafkaProducer<String, String>(prop);
		
		readJSON(producer,topic);
		
		/*try {
		File f = new File("/usr/local/hadoop/kafka_2.10-0.10.0.0/nohup.out");

        BufferedReader b = new BufferedReader(new FileReader(f));

        String readLine = "";

        System.out.println("Reading file using Buffered Reader");
        int i =0;
        while ((readLine = b.readLine()) != null) {
        	producer.send(new ProducerRecord<String, String>(topic,Integer.toString(++i),readLine));
        	Thread.sleep(3000);
        }
		
		} catch (IOException e) {
            e.printStackTrace();
        }
		*/
		/*//Producer<String, String> producer = new KafkaProducer<String, String>(prop);
		
		for(int i = 0; i < 10; i++){
			producer.send(new ProducerRecord<String, String>(topic,Integer.toString(i),Integer.toString(i)));
		}*/
		System.out.println("Message sent successfully !!");
		producer.close();
	}

	private static void readJSON(Producer<String, String> producer, String topic) {
		JSONParser parser = new JSONParser();
        try
        {
            Object object = parser
                    .parse(new FileReader("/usr/local/hadoop/kafka_2.10-0.10.0.0/example.json"));
            
            //convert Object to JSONObject
            JSONObject jsonObject = (JSONObject)object;
            
            //Reading the String
            String name = (String) jsonObject.get("Name");
            Long age = (Long) jsonObject.get("Age");
            
            //Reading the array
            JSONArray countries = (JSONArray)jsonObject.get("Countries");
            
            //Printing all the values
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Countries:");
            int i = 1;
            producer.send(new ProducerRecord<String, String>(topic,Integer.toString(i++),name + "," + age + "," + countries));
            /*for(Object country : countries)
            {
//                System.out.println("\t"+country.toString());
            	producer.send(new ProducerRecord<String, String>(topic,Integer.toString(i++),name + "," + age + "," + country));
            	
            }*/
        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
}
