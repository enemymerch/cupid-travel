package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.entity.Review;
import com.mcan.cupidtravel.service.impl.ReviewOnboardingServiceImpl;
import com.mcan.cupidtravel.utils.property.CupidProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReviewOnboardingServiceImplTest {

	@Mock
	private CupidProperties cupidProperties;

	@Mock
	private ReviewService reviewService;

	@Mock
	private CupidClient cupidClient;

	@InjectMocks
	@Spy
	private ReviewOnboardingServiceImpl reviewOnboardingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOnboardReviews() {
		Long[] hotelIds = {1L, 2L};
		when(cupidProperties.getHotelIds()).thenReturn(hotelIds);

		doNothing().when(reviewOnboardingService).onboardReviewByHotelId(anyLong());

		reviewOnboardingService.onboardReviews();

		verify(reviewOnboardingService, times(2)).onboardReviewByHotelId(anyLong());
	}

	@Test
	void testOnboardReviewByHotelId() {
		Long hotelId = 1L;
		Review mockReview = new Review();
		mockReview.setHotelId(hotelId);
		List<Review> reviews = Collections.singletonList(mockReview);
		List<Review> existingReviews = Collections.emptyList();

		when(cupidClient.getReviewByHotelId(hotelId)).thenReturn(reviews);
		when(reviewService.getAllReviewsByHotelId(hotelId)).thenReturn(existingReviews);

		reviewOnboardingService.onboardReviewByHotelId(hotelId);

		verify(reviewService).deleteAll(existingReviews);
		verify(reviewService).createAll(reviews);
	}

	@Test
	void testOnboardReviewByHotelId_WithException() {
		Long hotelId = 1L;

		when(cupidClient.getReviewByHotelId(hotelId)).thenThrow(new RuntimeException("Test Exception"));

		reviewOnboardingService.onboardReviewByHotelId(hotelId);

		verify(reviewService, never()).deleteAll(anyList());
		verify(reviewService, never()).createAll(anyList());
	}

}