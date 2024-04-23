package com.smartpark.service;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import com.smartpark.config.AppConstants;
@Component
public class KafkaTopicSaver {

	 private  Producer<String, String> producer;

	    public KafkaTopicSaver() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,AppConstants.BOOTSTRAP_SERVERS);
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	        this.producer = new KafkaProducer<>(props);
	    }
	    
	    public void saveToKafkaTopic(String message) {
	        ProducerRecord<String, String> record = new ProducerRecord<>(AppConstants.PARKINGSLOT_TOPIC_NAME, message);
	        producer.send(record, new Callback() {
	            @Override
	            public void onCompletion(RecordMetadata metadata, Exception exception) {
	                if (exception != null) {
	                    System.err.println("Error saving message to Kafka topic: " + exception.getMessage());
	                } else {
	                    System.out.println("Message saved successfully to Kafka topic. Topic: " + metadata.topic() +
	                            ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
	                }
	            }
	        });
	    }
	    public void close() {
	        producer.close();
	    }
}
