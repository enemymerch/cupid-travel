package com.mcan.cupidtravel.rest.controller;

import com.mcan.cupidtravel.rest.request.HotelSearchRequest;
import com.mcan.cupidtravel.service.HotelService;
import com.mcan.cupidtravel.service.entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelControllerTest {

	@Mock
	private HotelService hotelService;

	@InjectMocks
	private HotelController hotelController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetHotelById() {
		Long hotelId = 1L;
		Hotel mockHotel = new Hotel();
		when(hotelService.getById(hotelId)).thenReturn(mockHotel);

		ResponseEntity<Hotel> response = hotelController.getHotelById(hotelId);

		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertEquals(mockHotel, response.getBody());
		verify(hotelService).getById(hotelId);
	}

	@Test
	void testGetDetailedHotelById() {
		Long hotelId = 1L;
		Hotel mockHotel = new Hotel();
		when(hotelService.getDetailedById(hotelId)).thenReturn(mockHotel);

		ResponseEntity<Hotel> response = hotelController.getDetailedHotelById(hotelId);

		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertEquals(mockHotel, response.getBody());
		verify(hotelService).getDetailedById(hotelId);
	}

	@Test
	void testFilterHotelById() {
		HotelSearchRequest request = new HotelSearchRequest();
		Page<Hotel> mockPage = mock(Page.class);
		when(hotelService.search(any(PageRequest.class), eq(request))).thenReturn(mockPage);

		ResponseEntity<Page<Hotel>> response = hotelController.filterHotelById(request);

		assertNotNull(response);
		assertEquals(200, response.getStatusCode().value());
		assertEquals(mockPage, response.getBody());
		verify(hotelService).search(any(PageRequest.class), eq(request));
	}

	@Test
	void testCreate() {
		Hotel hotel = new Hotel();
		when(hotelService.create(hotel)).thenReturn(hotel);

		ResponseEntity<Hotel> response = hotelController.create(hotel);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(hotel, response.getBody());
		verify(hotelService).create(hotel);
	}

	@Test
	void testUpdate() {
		Long hotelId = 1L;
		Hotel hotel = new Hotel();
		when(hotelService.update(hotel)).thenReturn(hotel);

		ResponseEntity<Hotel> response = hotelController.update(hotelId, hotel);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(hotel, response.getBody());
		verify(hotelService).update(hotel);
	}

}