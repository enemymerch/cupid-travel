package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.FacilityTranslation;
import com.mcan.cupidtravel.service.impl.FacilityOnboardingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FacilityOnboardingServiceImplTest {

	@Mock
	private CupidClient cupidClient;

	@Mock
	private FacilityService facilityService;

	@InjectMocks
	private FacilityOnboardingServiceImpl facilityOnboardingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOnboardFacilities() {
		// Mock data setup
		List<Facility> mockFacilities = new ArrayList<>();
		Facility facility1 = new Facility();
		facility1.setFacilityId(1L);
		facility1.setFacility("Facility 1");
		facility1.setTranslation(new ArrayList<>());

		Facility facility2 = new Facility();
		facility2.setFacilityId(2L);
		facility2.setFacility("Facility 2");
		facility2.setTranslation(new ArrayList<>());

		mockFacilities.add(facility1);
		mockFacilities.add(facility2);

		when(cupidClient.getAllFacilities()).thenReturn(mockFacilities);
		when(facilityService.getAllFacilityIds()).thenReturn(Collections.singletonList(1L));
		when(facilityService.getById(1L)).thenReturn(facility1);

		// Call the method to test
		facilityOnboardingService.onboardFacilities();

		// Verify interactions and assertions
		verify(cupidClient).getAllFacilities();
		verify(facilityService).getAllFacilityIds();

		verify(facilityService).deleteAll(Collections.emptyList());
		verify(facilityService).create(facility2);

		// Verify translations are added
		assertEquals(1, facility1.getTranslation().size());
		FacilityTranslation translation1 = facility1.getTranslation().get(0);
		assertEquals("en", translation1.getLang());
		assertEquals("Facility 1", translation1.getFacility());

		assertEquals(1, facility2.getTranslation().size());
		FacilityTranslation translation2 = facility2.getTranslation().get(0);
		assertEquals("en", translation2.getLang());
		assertEquals("Facility 2", translation2.getFacility());
	}

}