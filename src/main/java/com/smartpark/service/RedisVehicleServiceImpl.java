package com.smartpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.smartpark.model.Vehicle;
import com.smartpark.repository.VehicleRedisRepo;


@Service
@ConditionalOnProperty(name = "db_flag", havingValue = "redis")
public class RedisVehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleRedisRepo repo;
	@Override
	public void addVehicle(Vehicle vehicle) {
		repo.save(vehicle);
	}

}
