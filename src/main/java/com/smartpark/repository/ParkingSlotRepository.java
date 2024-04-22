package com.smartpark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smartpark.model.Vehicle;

@Repository
public class ParkingSlotRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public void addParkingSlot(int slotNumber) {
		String sql = "INSERT INTO parking_slot (slot_number, is_occupied) VALUES (?, ?)";
		jdbcTemplate.update(sql, slotNumber, false);
	}

	public void removeParkingSlot(int slotId) {
		String sql = "DELETE FROM parking_slot WHERE  slot_number= ?";
		jdbcTemplate.update(sql, slotId);
	}

	public void parkVehicleOnSlot(int slotId, int vehicleId) {
		String sql = "UPDATE parking_slot SET is_occupied = true, vehicle_id = ? WHERE slot_number = ?";
		jdbcTemplate.update(sql, vehicleId, slotId);
	}

	public void emptySlot(int slotNum) {
		String sql = "UPDATE parking_slot SET is_occupied = false, vehicle_id = null WHERE slot_number = ?";
		jdbcTemplate.update(sql, slotNum);
	}
}
