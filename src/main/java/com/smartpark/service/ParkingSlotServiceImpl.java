package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartpark.repository.ParkingSlotRepository;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

	@Autowired
	private ParkingSlotRepository parkingSlotRepository;


	@Override
	public void addParkingSlot(Integer slotNum) {
		parkingSlotRepository.addParkingSlot(slotNum);
	}

	@Override
	public void removeParkingSlot(Integer slotId) {
		parkingSlotRepository.removeParkingSlot(slotId);

	}

	@Override
	public void parkVehicle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void emptySlot() {
		// TODO Auto-generated method stub

	}

}
