package com.smartpark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class VehicleRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void addVehicle(String licensePlate, String color, String model) {
		String sql = "INSERT INTO vehicle (license_plate, color, model) VALUES (?,?,?)";
		jdbcTemplate.update(sql, licensePlate, color, model);
	}


}
