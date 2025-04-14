package com.mcan.cupidtravel.service.impl;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
	public Long generateId() {
		return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	}

}