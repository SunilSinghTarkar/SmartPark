package com.smartpark.kafka;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartpark.config.AppConstants;
import com.smartpark.repository.ParkingSlotRedisRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.clients.jedis.Jedis;

@Component
public class KafkaTopicConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaTopicConsumer.class);

	private final Consumer<String, String> consumer;

	@Autowired
	private Jedis jedis;

	@Autowired
	private ParkingSlotRedisRepository redisRepo;

	@PostConstruct
	public void startConsumer() {
		Thread consumerThread = new Thread(this::consumeMessages);
		consumerThread.start();
	}

	public KafkaTopicConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, AppConstants.PARK_TOPIC_NAME);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(AppConstants.PARK_TOPIC_NAME));
	}

	public void consumeMessages() {
		
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				
				for (ConsumerRecord<String, String> record : records) {
					
					String message = record.value();
					logger.info("Received message: {}", message);
					processParkingEvent(message);
				}
			}
		} catch (Exception e) {
			logger.error("Error while consuming messages", e);
		} finally {
			closeConsumer();
		}
	}

	private void processParkingEvent(String message) {
		logger.info("inside processParkingEvent");
	
		if ("parked".equals(message)) {
			redisRepo.increaseParkedCount();
		} else if ("taken_out".equals(message)) {
			redisRepo.increaseUnParkedCount();
		}
	}

	@PreDestroy
	public void closeConsumer() {
		consumer.close();
	}
}
