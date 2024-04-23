package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.smartpark.model.Vehicle;
import com.smartpark.repository.ParkingSlotRepository;

@Service
@ConditionalOnProperty(name = "db_flag", havingValue = "mysql")
public class ParkingSlotServiceImpl implements ParkingSlotService {

	@Autowired
	private ParkingSlotRepository parkingSlotRepository;

	@Override
	public String addParkingSlot(Integer slotNum) {
		String response = parkingSlotRepository.addParkingSlot(slotNum);
		return response;
	}

	@Override
	public String removeParkingSlot(Integer slotId) {
		String response = parkingSlotRepository.removeParkingSlot(slotId);
		return response;
	}

	@Override
	public String parkVehicle(Integer slotNum, Integer vehicleId) {

		String response = parkingSlotRepository.parkVehicleOnSlot(slotNum, vehicleId);
		return response;
	}

	@Override
	public String emptySlot(Integer slotNum) {
		String response = parkingSlotRepository.emptySlot(slotNum);
		return response;
	}

	@Override
	public String addVehicle(Vehicle vehicle) {

		String response = parkingSlotRepository.addVehicle(vehicle.getLicensePlate(), vehicle.getColor(),
				vehicle.getModel());
		return response;
	}

	@Override
	public long getTotalParkedVehicles() {
		
		return 0;
	}

}
