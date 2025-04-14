package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.service.AmenityOnboardingService;
import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.RoomAmenityService;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.service.entity.RoomAmenityTranslation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AmenityOnboardingServiceImpl implements AmenityOnboardingService {
	private static final Logger log = LoggerFactory.getLogger(AmenityOnboardingServiceImpl.class);
	private final CupidClient cupidClient;
	private final RoomAmenityService roomAmenityService;


	public void onboardAmenities() {
		List<RoomAmenity> amenities = cupidClient.getAllAmenities();
		for (RoomAmenity amenity : amenities) {
			RoomAmenityTranslation defaultTransaction = new RoomAmenityTranslation();
			defaultTransaction.setLang("en");
			defaultTransaction.setAmenity(amenity.getAmenity());
			amenity.getTranslation().add(defaultTransaction);
		}


		List<Long> existingAmenityIds = roomAmenityService.getAllAmenityIds();
		List<Long> fetchedAmenityIds = amenities.stream().map(RoomAmenity::getAmenityId).toList();

		List<Long> tobeDeletedIds = existingAmenityIds.stream().filter(existingAmenityId -> !fetchedAmenityIds.contains(existingAmenityId)).toList();
		List<Long> tobeCreatedIds = new ArrayList<>();
		List<Long> tobeUpdatedIds = new ArrayList<>();
		fetchedAmenityIds.forEach(fetchedAmenityId -> {
			if (!existingAmenityIds.contains(fetchedAmenityId)) {
				tobeCreatedIds.add(fetchedAmenityId);
			} else {
				tobeUpdatedIds.add(fetchedAmenityId);
			}
		});

		roomAmenityService.deleteAll(tobeDeletedIds);
		amenities.stream().filter(facility -> tobeCreatedIds.contains(facility.getAmenityId())).forEach(roomAmenityService::create);
		amenities.stream().filter(facility -> tobeUpdatedIds.contains(facility.getAmenityId())).forEach(facility -> {
			if (!roomAmenityService.getById(facility.getAmenityId()).equals(facility)) {
				roomAmenityService.update(facility.getAmenityId(), facility);
			}
		});
		log.info("Onboarding of room amenities completed. Total facility # : {}", amenities.size());
	}

}
