package com.smartpark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingSlotRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addParkingSlot(int slotNumber) {
		String sql = "INSERT INTO parking_slot (slot_number, is_occupied) VALUES (?, ?)";
		jdbcTemplate.update(sql, slotNumber, false);
	}

	public void removeParkingSlot(int slotId) {
		String sql = "DELETE FROM parking_slot WHERE  slot_number= ?";
		jdbcTemplate.update(sql, slotId);
	}

}
