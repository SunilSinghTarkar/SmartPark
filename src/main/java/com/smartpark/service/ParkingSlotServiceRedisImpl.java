package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import com.smartpark.kafka.KafkaTopicProducer;
import com.smartpark.model.Vehicle;
import com.smartpark.repository.ParkingSlotRedisRepository;

@Service
@ConditionalOnProperty(name = "db_flag", havingValue = "redis")
public class ParkingSlotServiceRedisImpl implements ParkingSlotService {

	@Autowired
	private ParkingSlotRedisRepository parkingSlotRepo;

	@Autowired
	private KafkaTopicProducer kafkaTopicProducer;

	@Override
	public String addParkingSlot(Integer slotNum) {
		String response = parkingSlotRepo.addParkingSlot(slotNum);
		return response;
	}

	@Override
	public String removeParkingSlot(Integer slotNum) {
		String response = parkingSlotRepo.removeParkingSlot(slotNum);
		return response;
	}

	@Override
	public String parkVehicle(Integer slotNum, Integer vehicleId) {
		String response = parkingSlotRepo.parkVehicle(slotNum, vehicleId);
		kafkaTopicProducer.saveMessageToKafka("parked");
		return response;
	}

	@Override
	public String emptySlot(Integer slotNum) {
		String response = parkingSlotRepo.emptySlot(slotNum);
		kafkaTopicProducer.saveMessageToKafka("taken_out");
		
		return response;
	}

	@Override
	public String addVehicle(Vehicle vehicle) {
		String response = parkingSlotRepo.addVehicle(vehicle);
		return response;
	}

	@Override
	public long getTotalParkedVehicles() {
		long response = parkingSlotRepo.getTotalParkedVehicles();
		return response;
	}

}
