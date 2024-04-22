package com.smartpark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smartpark.model.Vehicle;

import redis.clients.jedis.Jedis;

@Repository
public class ParkingSlotRedisRepository {

	@Autowired
	private Jedis jedis;

	public String addParkingSlot(int slotNumber) {
		String key = "parking_slot:" + slotNumber;

		jedis.hset(key, "slotNumber", String.valueOf(slotNumber));
		jedis.hset(key, "isOccupied", "false");

		jedis.hset(key, "vehicleId", "");
		jedis.hset(key, "licensePlate", "");
		jedis.hset(key, "color", "");
		jedis.hset(key, "model", "");

		return "Parking slot added successfully.";
	}

	public String removeParkingSlot(Integer slotNum) {
		String key = "parking_slot:" + slotNum;

		if (jedis.exists(key)) {
			jedis.del(key);
			return "Parking slot removed successfully.";
		} else {
			return "Parking slot does not exist.";
		}
	}

	public String parkVehicle(Integer slotNum, Integer vehicleId) {
		String slotKey = "parking_slot:" + slotNum;

		if (jedis.exists(slotKey)) {
			String vehicleKey = "vehicle:" + vehicleId;
			if (jedis.exists(vehicleKey)) {
				jedis.hset(slotKey, "vehicleId", String.valueOf(vehicleId));
				jedis.hset(slotKey, "isOccupied", "true");
				return "Vehicle parked successfully Redis.";
			} else {
				return "Vehicle does not exist Redis.";
			}
		} else {
			return "Parking slot does not exist Redis.";
		}
	}

	
	public String emptySlot(Integer slotNum) {
	    String slotKey = "parking_slot:" + slotNum;
	    if (jedis.exists(slotKey)) {
	        jedis.hset(slotKey, "vehicleId", "");
	        jedis.hset(slotKey, "isOccupied", "false");
	        return "Slot " + slotNum + " emptied successfully.";
	    } else {
	        return "Parking slot does not exist.";
	    }
	}

	public String addVehicle(Vehicle vehicle) {
		String key = "vehicle:" + vehicle.getId();
		jedis.hset(key, "id", String.valueOf(vehicle.getId()));
		jedis.hset(key, "licensePlate", vehicle.getLicensePlate());
		jedis.hset(key, "color", vehicle.getColor());
		jedis.hset(key, "model", vehicle.getModel());

		return "Vehicle added successfully Redis";
	}
}
