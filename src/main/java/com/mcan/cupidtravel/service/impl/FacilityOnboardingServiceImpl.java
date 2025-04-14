package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.FacilityService;
import com.mcan.cupidtravel.service.FacilityOnboardingService;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.FacilityTranslation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FacilityOnboardingServiceImpl implements FacilityOnboardingService {
	private static final Logger log = LoggerFactory.getLogger(FacilityOnboardingServiceImpl.class);
	private final FacilityService facilityService;
	private final CupidClient cupidClient;


	@Override
	public void onboardFacilities() {
		List<Facility> facilities = cupidClient.getAllFacilities();
		for (Facility facility : facilities) {
			FacilityTranslation defaultTransaction = new FacilityTranslation();
			defaultTransaction.setLang("en");
			defaultTransaction.setFacility(facility.getFacility());
			facility.getTranslation().add(defaultTransaction);
		}

		List<Long> existingFacilityIds = facilityService.getAllFacilityIds();
		List<Long> fetchedFacilityIds = facilities.stream().map(Facility::getFacilityId).toList();

		List<Long> tobeDeletedReviews = existingFacilityIds.stream().filter(existingFacilityId -> !fetchedFacilityIds.contains(existingFacilityId)).toList();
		List<Long> tobeCreatedReviews = new ArrayList<>();
		List<Long> tobeUpdatedReviews = new ArrayList<>();
		fetchedFacilityIds.forEach(fetchedFacilityId -> {
			if (!existingFacilityIds.contains(fetchedFacilityId)) {
				tobeCreatedReviews.add(fetchedFacilityId);
			} else {
				tobeUpdatedReviews.add(fetchedFacilityId);
			}
		});

		facilityService.deleteAll(tobeDeletedReviews);
		facilities.stream().filter(facility -> tobeCreatedReviews.contains(facility.getFacilityId())).forEach(facilityService::create);
		facilities.stream().filter(facility -> tobeUpdatedReviews.contains(facility.getFacilityId())).forEach(facility -> {
			if (!facilityService.getById(facility.getFacilityId()).equals(facility)) {
				facilityService.update(facility.getFacilityId(), facility);
			}
		});
		log.info("Onboarding of facilities completed. Total facility # : {}", facilities.size());
	}

}
