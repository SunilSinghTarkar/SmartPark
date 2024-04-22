package com.smartpark.repository;

import org.springframework.data.repository.CrudRepository;

import com.smartpark.model.Vehicle;

public interface VehicleRedisRepo extends CrudRepository<Vehicle, String> {

	Vehicle findById(Integer vehicleId);
}

