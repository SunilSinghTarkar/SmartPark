package com.smartpark.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class Vehicle{

	private int id;
	private String licensePlate;
	private String color;
	private String model;
  
	public Vehicle() {

	}

	public Vehicle(int id, String licensePlate, String color, String model) {
		super();
		this.id = id;
		this.licensePlate = licensePlate;
		this.color = color;
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
