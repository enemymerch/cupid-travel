package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.Room;
import com.mcan.cupidtravel.service.impl.HotelOnboardingServiceImpl;
import com.mcan.cupidtravel.utils.constant.Languages;
import com.mcan.cupidtravel.utils.property.CupidProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class HotelOnboardingServiceImplTest {

	@Mock
	private CupidProperties cupidProperties;

	@Mock
	private HotelService hotelService;

	@Mock
	private CupidClient cupidClient;

	@InjectMocks
	@Spy
	private HotelOnboardingServiceImpl hotelOnboardingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOnboardHotels_withDeadLetters() {
		when(cupidProperties.getHotelIds()).thenReturn(new Long[] {3L, 4L});

		doNothing().when(hotelOnboardingService).onboardHotelById(anyLong());

		hotelOnboardingService.onboardHotels();

		verify(hotelOnboardingService, times(2)).onboardHotelById(anyLong());
	}

	@Test
	void testOnboardHotels_withoutDeadLetters() {
		when(cupidProperties.getHotelIds()).thenReturn(new Long[] {3L, 4L});

		doNothing().when(hotelOnboardingService).onboardHotelById(anyLong());

		hotelOnboardingService.onboardHotels();

		verify(hotelOnboardingService, times(1)).onboardHotelById(3L);
		verify(hotelOnboardingService, times(1)).onboardHotelById(4L);
	}

	@Test
	void testOnboardHotelById() {
		Long hotelId = 1L;
		Hotel mockHotel = new Hotel();
		List<Room> mockRooms = new ArrayList<>();
		mockRooms.add(Room.builder().id(1L).build()); // Add a dummy room
		mockHotel.setRooms(mockRooms); // Ensure rooms is not null

		when(cupidClient.getHotelContent(hotelId)).thenReturn(mockHotel);
		when(cupidClient.getHotelTranslation(hotelId, Languages.FRENCH)).thenReturn(mockHotel);
		when(cupidClient.getHotelTranslation(hotelId, Languages.SPANISH)).thenReturn(mockHotel);

		hotelOnboardingService.onboardHotelById(hotelId);

		verify(hotelService).create(mockHotel);
	}

}