package com.smartpark.repository;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smartpark.model.Vehicle;

import redis.clients.jedis.Jedis;

@Repository
public class ParkingSlotRedisRepository {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSlotRedisRepository.class);

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

        logger.info("Parking slot added successfully.");
        return "Parking slot added successfully.";
    }

    public String removeParkingSlot(Integer slotNum) {
        String key = "parking_slot:" + slotNum;

        if (jedis.exists(key)) {
            jedis.del(key);
            logger.info("Parking slot removed successfully.");
            return "Parking slot removed successfully.";
        } else {
            logger.warn("Parking slot does not exist.");
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
                logger.info("Vehicle parked successfully in slot {}.", slotNum);
                return "Vehicle parked successfully Redis.";
            } else {
                logger.warn("Vehicle with ID {} does not exist.", vehicleId);
                return "Vehicle does not exist Redis.";
            }
        } else {
            logger.warn("Parking slot {} does not exist.", slotNum);
            return "Parking slot does not exist Redis.";
        }
    }

    public String emptySlot(Integer slotNum) {
        String slotKey = "parking_slot:" + slotNum;
        if (jedis.exists(slotKey)) {
            jedis.hset(slotKey, "vehicleId", "");
            jedis.hset(slotKey, "isOccupied", "false");
            logger.info("Slot {} emptied successfully.", slotNum);
            return "Slot " + slotNum + " emptied successfully.";
        } else {
            logger.warn("Parking slot {} does not exist.", slotNum);
            return "Parking slot does not exist.";
        }
    }

    public long getTotalParkedVehicles() {
        Set<String> parkingSlotKeys = jedis.keys("parking_slot:*");

        long totalParked = parkingSlotKeys.stream().map(jedis::hgetAll)
                .filter(slot -> Boolean.parseBoolean(slot.get("isOccupied"))).count();
        logger.info("Total parked vehicles: {}.", totalParked);
        return totalParked;
    }

    public String addVehicle(Vehicle vehicle) {
        String key = "vehicle:" + vehicle.getId();
        jedis.hset(key, "id", String.valueOf(vehicle.getId()));
        jedis.hset(key, "licensePlate", vehicle.getLicensePlate());
        jedis.hset(key, "color", vehicle.getColor());
        jedis.hset(key, "model", vehicle.getModel());

        logger.info("Vehicle added successfully with ID {}.", vehicle.getId());
        return "Vehicle added successfully Redis";
    }

    public long increaseParkedCount() {
        long parkedCount = jedis.hincrBy("Vehicle_Activity", "parkedCount", 1);
        logger.info("Parked count increased to {}.", parkedCount);
        return parkedCount;
    }

    public long increaseUnParkedCount() {
        long unParkedCount = jedis.hincrBy("Vehicle_Activity", "unParkedCount", 1);
        logger.info("Unparked count increased to {}.", unParkedCount);
        return unParkedCount;
    }
}
