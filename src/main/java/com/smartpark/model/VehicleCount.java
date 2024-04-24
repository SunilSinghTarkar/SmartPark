package com.smartpark.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("parkingLot_activity")
public class VehicleCount {

	private long parkedCount;
	private long unParkedCount;
}
