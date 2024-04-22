package com.smartpark.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartpark.model.Vehicle;
import com.smartpark.service.ParkingSlotService;
import com.smartpark.service.VehicleService;

@RestController
public class ParkingController {
	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private ParkingSlotService parkingSlotService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

	// Endpoint for add a parking slot
	@PostMapping("/addSlot/{slotNum}")
	public ResponseEntity<String> addParkingSlot(@PathVariable Integer slotNum) {
		parkingSlotService.addParkingSlot(slotNum);
		return ResponseEntity.status(HttpStatus.CREATED).body("Parking slot added successfully.");
	}
	
	 // Endpoint for remove a parking slot
    @DeleteMapping("/removeSlot/{slotId}")
    public ResponseEntity<String> removeParkingSlot(@PathVariable Integer slotId) {
        parkingSlotService.removeParkingSlot(slotId);
        return ResponseEntity.ok("Parking slot removed successfully.");
    }

    //Endpoint for add a vehicle
	@PostMapping("/addVehicle")
	public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle) {
		try {
			vehicleService.addVehicle(vehicle);
			return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle added successfully.");
		} catch (DataAccessException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding vehicle.");
		}
	}

}
