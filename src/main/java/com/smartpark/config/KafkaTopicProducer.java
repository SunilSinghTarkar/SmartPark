package com.smartpark.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpark.model.Vehicle;

import jakarta.annotation.PreDestroy;

@Component
public class KafkaTopicProducer {

	private Producer<String, String> producer;

	public KafkaTopicProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		this.producer = new KafkaProducer<>(props);
		
	}

	public void saveToKafkaTopic(String message) {
		ProducerRecord<String, String> record = new ProducerRecord<>(AppConstants.PARK_TOPIC_NAME, message);
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					
					System.err.println("Error saving message to Kafka topic: " + exception.getMessage());
				} else {
					System.out.println("Message saved successfully to Kafka topic.");
				}
			}
		});
	}

	public void saveMessageToKafka(String message) {
		ProducerRecord<String, String> record = new ProducerRecord<>(AppConstants.PARK_TOPIC_NAME, message);
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					
					System.err.println("Error saving message to Kafka topic: " + exception.getMessage());
				} else {
					System.out.println("Message saved successfully to Kafka topic.");
				}
			}
		});
	}
    @PreDestroy
	public void close() {
		producer.close();
	}
}
