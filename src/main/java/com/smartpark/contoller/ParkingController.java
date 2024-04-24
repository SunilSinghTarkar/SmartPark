package com.smartpark.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartpark.model.Vehicle;
import com.smartpark.service.ParkingSlotService;

@RestController
@RequestMapping("/parkingSlot")
public class ParkingController {

	@Autowired
	private ParkingSlotService parkingSlotService;

	// Endpoint to get Number of Total Parked Vehicles
	@GetMapping
	public ResponseEntity<Long> getTotalParkedVehicles() {
		long response = parkingSlotService.getTotalParkedVehicles();
		return ResponseEntity.ok(response);
	}

	// Endpoint for add a vehicle
	@PostMapping("/vehicle")
	public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle) {

		String response = parkingSlotService.addVehicle(vehicle);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	// Endpoint for add a parking slot
	@PostMapping("/{slotNum}")
	public ResponseEntity<String> addParkingSlot(@PathVariable Integer slotNum) {
		String response = parkingSlotService.addParkingSlot(slotNum);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Endpoint for remove a parking slot
	@DeleteMapping("/{slotNum}")
	public ResponseEntity<String> removeParkingSlot(@PathVariable Integer slotNum) {
		String response = parkingSlotService.removeParkingSlot(slotNum);
		return ResponseEntity.ok(response);
	}

	// Endpoint to park a vehicle on a slot
	@PostMapping("/parkVehicle/{slotNum}/{vehicleId}")
	public ResponseEntity<String> putVehicleOnSlot(@PathVariable Integer slotNum, @PathVariable Integer vehicleId) {
		String response = parkingSlotService.parkVehicle(slotNum, vehicleId);
		return ResponseEntity.ok(response);
	}

	// Endpoint to empty a slot
	@PutMapping("/emptySlot/{slotNum}")
	public ResponseEntity<String> emptySlot(@PathVariable Integer slotNum) {
		String response = parkingSlotService.emptySlot(slotNum);
		return ResponseEntity.ok(response);
	}

}
