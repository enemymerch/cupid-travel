package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.service.entity.RoomAmenityTranslation;
import com.mcan.cupidtravel.service.impl.AmenityOnboardingServiceImpl;
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

class AmenityOnboardingServiceImplTest {

	@Mock
	private CupidClient cupidClient;

	@Mock
	private RoomAmenityService roomAmenityService;

	@InjectMocks
	private AmenityOnboardingServiceImpl amenityOnboardingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOnboardAmenities() {
		List<RoomAmenity> mockAmenities = new ArrayList<>();
		RoomAmenity amenity1 = new RoomAmenity();
		amenity1.setAmenityId(1L);
		amenity1.setAmenity("Amenity 1");
		amenity1.setTranslation(new ArrayList<>());

		RoomAmenity amenity2 = new RoomAmenity();
		amenity2.setAmenityId(2L);
		amenity2.setAmenity("Amenity 2");
		amenity2.setTranslation(new ArrayList<>());

		mockAmenities.add(amenity1);
		mockAmenities.add(amenity2);

		when(cupidClient.getAllAmenities()).thenReturn(mockAmenities);
		when(roomAmenityService.getAllAmenityIds()).thenReturn(Collections.singletonList(1L));
		when(roomAmenityService.getById(1L)).thenReturn(amenity1);

		amenityOnboardingService.onboardAmenities();

		verify(cupidClient).getAllAmenities();
		verify(roomAmenityService).getAllAmenityIds();

		verify(roomAmenityService).deleteAll(Collections.emptyList());
		verify(roomAmenityService).create(amenity2);

		assertEquals(1, amenity1.getTranslation().size());
		RoomAmenityTranslation translation1 = amenity1.getTranslation().get(0);
		assertEquals("en", translation1.getLang());
		assertEquals("Amenity 1", translation1.getAmenity());

		assertEquals(1, amenity2.getTranslation().size());
		RoomAmenityTranslation translation2 = amenity2.getTranslation().get(0);
		assertEquals("en", translation2.getLang());
		assertEquals("Amenity 2", translation2.getAmenity());
	}

}