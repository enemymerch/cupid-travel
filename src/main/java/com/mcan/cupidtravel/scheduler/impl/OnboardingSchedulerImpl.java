package com.mcan.cupidtravel.scheduler.impl;

import com.mcan.cupidtravel.scheduler.OnboardingScheduler;
import com.mcan.cupidtravel.service.AmenityOnboardingService;
import com.mcan.cupidtravel.service.FacilityOnboardingService;
import com.mcan.cupidtravel.service.HotelOnboardingService;
import com.mcan.cupidtravel.service.ReviewOnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnboardingSchedulerImpl implements OnboardingScheduler {

	private final FacilityOnboardingService facilityOnboardingService;
	private final AmenityOnboardingService amenityOnboardingService;
	private final HotelOnboardingService hotelOnboardingService;
	private final ReviewOnboardingService reviewOnboardingService;

	@Override
	@Scheduled(fixedRate = 10000)
	public void onboard() {
		amenityOnboardingService.onboardAmenities();
		facilityOnboardingService.onboardFacilities();
		hotelOnboardingService.onboardHotels();
		reviewOnboardingService.onboardReviews();
	}

}
