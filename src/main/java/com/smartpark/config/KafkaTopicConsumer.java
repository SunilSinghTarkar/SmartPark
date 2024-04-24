package com.smartpark.config;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpark.model.Vehicle;
import com.smartpark.repository.ParkingSlotRedisRepository;

import jakarta.annotation.PreDestroy;
import redis.clients.jedis.Jedis;

@Component
public class KafkaTopicConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Jedis jedis;
    private final ParkingSlotRedisRepository redisRepo;
    private final Consumer<String, String> consumer;

    @Autowired
    public KafkaTopicConsumer(Jedis jedis, ParkingSlotRedisRepository redisRepo) {
        this.jedis = jedis;
        this.redisRepo = redisRepo;
        this.consumer = createConsumer();
    }

    private Consumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return new KafkaConsumer<>(props);
    }

    public void consumeMessages() {
        try {
            consumer.subscribe(Collections.singletonList(AppConstants.PARK_TOPIC_NAME));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    prodcessParkingSlotEvent(record.value());
                }
                consumer.commitAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void consumeMessagesAndSaveToRedis() {
        try {
            consumer.subscribe(Collections.singletonList(AppConstants.VEHICLE_TOPIC_NAME));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String jsonData = record.value();
                    Vehicle vehicle = objectMapper.readValue(jsonData, Vehicle.class);
                    jedis.hset("vehicles:" + vehicle.getId(), String.valueOf(vehicle.getId()), jsonData);
                }
                consumer.commitAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    private void prodcessParkingSlotEvent(String value) {
        if ("parked".equals(value)) {
            redisRepo.increaseParkedCount();
        } else {
            redisRepo.increaseUnParkedCount();
        }
    }
    
    @PreDestroy
    public void closeConsumer() {
        consumer.close();
    }

}
