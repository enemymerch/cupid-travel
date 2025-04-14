package com.mcan.cupidtravel.rest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcan.cupidtravel.rest.client.impl.CupidClientImpl;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.Review;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.utils.property.CupidProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CupidClientImplTest {

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private CupidProperties cupidProperties;

	@InjectMocks
	private CupidClientImpl cupidClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		cupidClient = spy(new CupidClientImpl(objectMapper, cupidProperties));
	}

	@Test
	void testGetHotelContent() throws Exception {
		Long hotelId = 1L;
		Hotel mockHotel = new Hotel();
		String apiUrl = "http://api.example.com/";
		String jsonResponse = "{\"hotelId\":1}";

		when(cupidProperties.getApiUrl()).thenReturn(apiUrl);
		when(objectMapper.readValue(jsonResponse, new TypeReference<Hotel>() {
		})).thenReturn(mockHotel);
		doReturn(mockHotel).when(cupidClient).doHttpRequestToCupid(anyString(), any(TypeReference.class));

		Hotel result = cupidClient.getHotelContent(hotelId);

		assertNotNull(result);
		verify(cupidProperties).getApiUrl();
	}

	@Test
	void testGetHotelTranslation() throws Exception {
		Long hotelId = 1L;
		String lang = "en";
		Hotel mockHotel = new Hotel();
		String apiUrl = "http://api.example.com/";
		String translationPath = "/translations/";
		String jsonResponse = "{\"hotelId\":1}";

		when(cupidProperties.getApiUrl()).thenReturn(apiUrl);
		when(cupidProperties.getHotelTranslationApiUrlPath()).thenReturn(translationPath);
		when(objectMapper.readValue(jsonResponse, new TypeReference<Hotel>() {
		})).thenReturn(mockHotel);
		doReturn(mockHotel).when(cupidClient).doHttpRequestToCupid(anyString(), any(TypeReference.class));

		Hotel result = cupidClient.getHotelTranslation(hotelId, lang);

		assertNotNull(result);
		verify(cupidProperties).getApiUrl();
		verify(cupidProperties).getHotelTranslationApiUrlPath();
	}

	@Test
	void testGetReviewByHotelId() throws Exception {
		Long hotelId = 1L;
		List<Review> mockReviews = Collections.singletonList(new Review());
		String apiUrl = "http://api.example.com/";
		String reviewPath = "/reviews/";
		String jsonResponse = "[{}]";

		when(cupidProperties.getApiUrl()).thenReturn(apiUrl);
		when(cupidProperties.getReviewApiUrlPath()).thenReturn(reviewPath);
		when(cupidProperties.getReviewApiBatchSize()).thenReturn(10);
		when(objectMapper.readValue(jsonResponse, new TypeReference<List<Review>>() {
		})).thenReturn(mockReviews);
		doReturn(mockReviews).when(cupidClient).doHttpRequestToCupid(anyString(), any(TypeReference.class));

		List<Review> result = cupidClient.getReviewByHotelId(hotelId);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(cupidProperties).getApiUrl();
		verify(cupidProperties).getReviewApiUrlPath();
		verify(cupidProperties).getReviewApiBatchSize();
	}

	@Test
	void testGetAllFacilities() throws Exception {
		List<Facility> mockFacilities = Collections.singletonList(new Facility());
		String apiUrl = "http://api.example.com/";
		String facilityPath = "/facilities/";
		String jsonResponse = "[{}]";

		when(cupidProperties.getApiUrl()).thenReturn(apiUrl);
		when(cupidProperties.getFacilityApiUrlPath()).thenReturn(facilityPath);
		when(objectMapper.readValue(jsonResponse, new TypeReference<List<Facility>>() {
		})).thenReturn(mockFacilities);
		doReturn(mockFacilities).when(cupidClient).doHttpRequestToCupid(anyString(), any(TypeReference.class));

		List<Facility> result = cupidClient.getAllFacilities();

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(cupidProperties).getApiUrl();
		verify(cupidProperties).getFacilityApiUrlPath();
	}

	@Test
	void testGetAllAmenities() throws Exception {
		List<RoomAmenity> mockAmenities = Collections.singletonList(new RoomAmenity());
		String apiUrl = "http://api.example.com/";
		String amenityPath = "/amenities/";
		String jsonResponse = "[{}]";

		when(cupidProperties.getApiUrl()).thenReturn(apiUrl);
		when(cupidProperties.getAmenityApiUrlPath()).thenReturn(amenityPath);
		when(objectMapper.readValue(jsonResponse, new TypeReference<List<RoomAmenity>>() {
		})).thenReturn(mockAmenities);
		doReturn(mockAmenities).when(cupidClient).doHttpRequestToCupid(anyString(), any(TypeReference.class));

		List<RoomAmenity> result = cupidClient.getAllAmenities();

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(cupidProperties).getApiUrl();
		verify(cupidProperties).getAmenityApiUrlPath();
	}

}