package com.smartpark.service;

import com.smartpark.model.Vehicle;

public interface ParkingSlotService {

	public String addParkingSlot(Integer slotNum);

	public String removeParkingSlot(Integer slotId);

	public String parkVehicle(Integer slotNum, Integer vehicleId);

	public String emptySlot(Integer slotNum);
	
	 public String addVehicle(Vehicle vehicle);

}
