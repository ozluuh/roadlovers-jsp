package com.roadlovers.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Vehicle {
	
	private Long id;
	
	private int year;
	
	private String model;
	
	private double value;
	
	private LocalDateTime createdAt;

	private VehicleType vehicleType;

	private Manufacturer manufacturer;
}
