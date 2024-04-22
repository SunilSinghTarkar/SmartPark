package com.smartpark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ParkingSlotRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String addParkingSlot(int slotNumber) {
		String sql = "INSERT INTO parking_slot (slot_number, is_occupied) VALUES (?, ?)";

		try {
			jdbcTemplate.update(sql, slotNumber, false);
			return "Parking slot added successfully.";
		} catch (DataAccessException e) {
			return "Failed to add parking slot.";
		}
	}

	public String removeParkingSlot(int slotId) {
		String sql = "DELETE FROM parking_slot WHERE  slot_number= ?";
		try {
			jdbcTemplate.update(sql, slotId);
			return "Parking slot removed successfully.";
		} catch (DataAccessException e) {
			return "Failed to remove parking slot.";
		}
	}

	public String parkVehicleOnSlot(int slotId, int vehicleId) {
		String sql = "UPDATE parking_slot SET is_occupied = true, vehicle_id = ? WHERE slot_number = ?";
		try {
			jdbcTemplate.update(sql, vehicleId, slotId);
			return "Vehicle parked successfully.";
		} catch (DataAccessException e) {
			return "Failed to park vehicle.";
		}
	}

	public String emptySlot(int slotNum) {
		String sql = "UPDATE parking_slot SET is_occupied = false, vehicle_id = null WHERE slot_number = ?";
		try {
			jdbcTemplate.update(sql, slotNum);
			return "Slot emptied successfully.";
		} catch (DataAccessException e) {
			return "Failed to empty slot.";
		}
	}
	
	public String addVehicle(String licensePlate, String color, String model) {

		String sql = "INSERT INTO vehicle (license_plate, color, model) VALUES (?,?,?)";

		try {
			jdbcTemplate.update(sql, licensePlate, color, model);
			return "Vehicle added successfully.";
		} catch (DuplicateKeyException e) {
			return "Failed to add vehicle: already exists.";
		} catch (DataAccessException e) {
			return "Failed to add vehicle";
		}

	}
}
