package com.smartpark.repository;

import org.springframework.data.repository.CrudRepository;

import com.smartpark.model.ParkingSlot;

public interface ParkingSlotRedisRepository extends CrudRepository<ParkingSlot, Integer>{

	ParkingSlot findBySlotNumber(Integer slotNum);

	

}
