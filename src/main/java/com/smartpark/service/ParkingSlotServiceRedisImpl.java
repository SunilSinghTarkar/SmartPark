package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.smartpark.model.ParkingSlot;
import com.smartpark.model.Vehicle;
import com.smartpark.repository.ParkingSlotRedisRepository;
import com.smartpark.repository.VehicleRedisRepo;

@Service
@ConditionalOnProperty(name = "db_flag", havingValue = "redis")
public class ParkingSlotServiceRedisImpl implements ParkingSlotService {

	@Autowired
	private ParkingSlotRedisRepository parkingSlotRedisRepositoryl;

	@Autowired
	private VehicleRedisRepo vehicleRepo;

	@Override
	public void addParkingSlot(Integer slotNum) {

		ParkingSlot parkingSlot = new ParkingSlot();
		parkingSlot.setSlotNumber(slotNum);

		parkingSlotRedisRepositoryl.save(parkingSlot);

	}

	@Override
	public void removeParkingSlot(Integer slotNum) {
		parkingSlotRedisRepositoryl.findBySlotNumber(slotNum);

	}

	@Override
	public void parkVehicle(Integer slotNum, Integer vehicleId) {
		ParkingSlot parkingSlot = parkingSlotRedisRepositoryl.findBySlotNumber(slotNum);

		Vehicle vehicle = vehicleRepo.findById(vehicleId);

		if (parkingSlot != null) {
			parkingSlot.setVehicle(vehicle);
			parkingSlot.setOccupied(true);
			parkingSlotRedisRepositoryl.save(parkingSlot);
		} else {

			throw new RuntimeException("Something went wrong");

		}

	}

	@Override
	public void emptySlot(Integer slotNum) {
		ParkingSlot parkingSlot = parkingSlotRedisRepositoryl.findBySlotNumber(slotNum);

		if (parkingSlot != null) {
			parkingSlot.setOccupied(false);
			parkingSlot.setVehicle(null);
			parkingSlotRedisRepositoryl.save(parkingSlot);
		} else {
			throw new RuntimeException("Something went wrong");
		}

	}

}
