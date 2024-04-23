package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

//import com.smartpark.config.AppConstants;
//import com.smartpark.model.ParkingSlot;
import com.smartpark.model.Vehicle;
import com.smartpark.repository.ParkingSlotRedisRepository;

@Service
@ConditionalOnProperty(name = "db_flag", havingValue = "redis")
public class ParkingSlotServiceRedisImpl implements ParkingSlotService {

	@Autowired
	private ParkingSlotRedisRepository parkingSlotRepo;

	@Autowired
	private KafkaTopicSaver topicSaver;

//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
	@Override
	public String addParkingSlot(Integer slotNum) {
		String response = parkingSlotRepo.addParkingSlot(slotNum);
//		this.kafkaTemplate.send(AppConstants.PARKINGSLOT_TOPIC_NAME,"Parking slot Added successfully slotNum: "+slotNum);
		return response;
	}

	@Override
	public String removeParkingSlot(Integer slotNum) {
		String response = parkingSlotRepo.removeParkingSlot(slotNum);
//		this.kafkaTemplate.send(AppConstants.PARKINGSLOT_TOPIC_NAME,"Parking slot removed successfully slotNum: "+slotNum);
		return response;
	}

	@Override
	public String parkVehicle(Integer slotNum, Integer vehicleId) {
		String response = parkingSlotRepo.parkVehicle(slotNum, vehicleId);
//		this.kafkaTemplate.send(AppConstants.PARKINGSLOT_TOPIC_NAME,"Vehicle Parked Successfully at SlotNum : "+slotNum +" .");
		return response;
	}

	@Override
	public String emptySlot(Integer slotNum) {
		String response = parkingSlotRepo.emptySlot(slotNum);
//		this.kafkaTemplate.send(AppConstants.PARKINGSLOT_TOPIC_NAME,"Vehicle emptied Successfully at SlotNum : "+slotNum +" .");
		return response;
	}

	@Override
	public String addVehicle(Vehicle vehicle) {
		String response = parkingSlotRepo.addVehicle(vehicle);
//		this.kafkaTemplate.send(AppConstants.PARKINGSLOT_TOPIC_NAME,"Vehicle Added Successfully : "+vehicle.getLicensePlate());

		topicSaver.saveToKafkaTopic("Vehicle Added Successfully : " + vehicle.getLicensePlate());

		return response;
	}

	@Override
	public long getTotalParkedVehicles() {
		long response = parkingSlotRepo.getTotalParkedVehicles();
		return response;
	}

}
