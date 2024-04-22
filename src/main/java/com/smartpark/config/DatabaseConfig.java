package com.smartpark.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class DatabaseConfig {
	
	    @Value("${spring.data.redis.host}")
	    private String redisHost;

	    @Value("${spring.data.redis.port}")
	    private int redisPort;

	    @Value("${spring.data.redis.password}")
	    private String redisPassword; 
	
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/smartPark");
		dataSource.setUsername("cloud");
		dataSource.setPassword("scape");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	 @Bean
	    public LettuceConnectionFactory redisConnectionFactory() {
	        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
	        redisConfig.setPassword(redisPassword);
	        return new LettuceConnectionFactory(redisConfig);
	    }
	 
	   @Bean
	     public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
	         RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	         redisTemplate.setConnectionFactory(redisConnectionFactory);
	         return redisTemplate;
	     }
}
