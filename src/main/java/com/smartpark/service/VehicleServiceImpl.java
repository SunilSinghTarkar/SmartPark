package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartpark.model.Vehicle;
import com.smartpark.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Override
	public void addVehicle(Vehicle vehicle) {
        vehicleRepository.addVehicle(vehicle.getLicensePlate(), vehicle.getColor(), vehicle.getModel());

	}

	@Override
	public void removeVehicle() {
		

	}

}
