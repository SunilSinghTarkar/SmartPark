package com.smartpark.service;

public interface ParkingSlotService {
  
	public void addParkingSlot(Integer slotNum);
	public void removeParkingSlot(Integer slotId);
	public void parkVehicle(Integer slotNum,Integer vehicleId);
	public void emptySlot(Integer slotNum);
	
}
