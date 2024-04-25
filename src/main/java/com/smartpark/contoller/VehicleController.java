package com.smartpark.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartpark.model.Vehicle;
import com.smartpark.service.ParkingSlotService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	private ParkingSlotService parkingSlotService;

	// Endpoint for add a vehicle
	@PostMapping
	public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle) {

		String response = parkingSlotService.addVehicle(vehicle);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}
}
