package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.service.impl.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdGeneratorTest {

	private IdGenerator idGenerator;

	@BeforeEach
	void setUp() {
		idGenerator = new IdGenerator();
	}

	@Test
	void testGenerateId() {
		Long id = idGenerator.generateId();

		assertNotNull(id, "Generated ID should not be null");
		assertTrue(id > 0, "Generated ID should be a positive number");
	}

}