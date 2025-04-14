package com.mcan.cupidtravel.scheduler;

import com.mcan.cupidtravel.scheduler.impl.OnboardingSchedulerImpl;
import com.mcan.cupidtravel.service.AmenityOnboardingService;
import com.mcan.cupidtravel.service.FacilityOnboardingService;
import com.mcan.cupidtravel.service.HotelOnboardingService;
import com.mcan.cupidtravel.service.ReviewOnboardingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class OnboardingSchedulerImplTest {

	@Mock
	private AmenityOnboardingService amenityOnboardingService;

	@Mock
	private FacilityOnboardingService facilityOnboardingService;

	@Mock
	private HotelOnboardingService hotelOnboardingService;

	@Mock
	private ReviewOnboardingService reviewOnboardingService;

	@InjectMocks
	private OnboardingSchedulerImpl onboardingScheduler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOnboard() {
		onboardingScheduler.onboard();

		verify(amenityOnboardingService).onboardAmenities();
		verify(facilityOnboardingService).onboardFacilities();
		verify(hotelOnboardingService).onboardHotels();
		verify(reviewOnboardingService).onboardReviews();
	}

}