package com.smartpark.config;

//import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;

import redis.clients.jedis.Jedis;

@Configuration
public class DatabaseConfig {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	@Value("${spring.data.redis.password}")
	private String redisPassword;

	@Bean
	public Jedis jedisConnectionFactory() {
		Jedis jedis = new Jedis(redisHost, redisPort);
		jedis.auth(redisPassword);
		return jedis;
	}

//	@Bean
//	public NewTopic newTopic() {
//
//		return TopicBuilder.name(AppConstants.PARKINGSLOT_TOPIC_NAME).build();
//	}
}
