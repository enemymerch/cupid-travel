package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.ReviewOnboardingService;
import com.mcan.cupidtravel.service.ReviewService;
import com.mcan.cupidtravel.service.entity.Review;
import com.mcan.cupidtravel.utils.property.CupidProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewOnboardingServiceImpl implements ReviewOnboardingService {

	private static final Logger log = LoggerFactory.getLogger(HotelOnboardingServiceImpl.class);

	private final CupidProperties cupidProperties;
	private final ReviewService reviewService;
	private final CupidClient cupidClient;

	public void onboardReviews() {
		for (Long hotelId : cupidProperties.getHotelIds()) {
			onboardReviewByHotelId(hotelId);
		}
	}

	public void onboardReviewByHotelId(Long hotelId) {
		try {
			List<Review> reviews = cupidClient.getReviewByHotelId(hotelId);

			List<Review> existingReviews = reviewService.getAllReviewsByHotelId(hotelId);
			List<Review> tobeDeletedReviews = existingReviews.stream().filter(existingReview -> !reviews.contains(existingReview)).toList();
			List<Review> tobeCreatedReviews = reviews.stream().filter(review -> !existingReviews.contains(review)).toList();

			reviewService.deleteAll(tobeDeletedReviews);
			tobeCreatedReviews.forEach(review -> review.setHotelId(hotelId));
			reviewService.createAll(tobeCreatedReviews);
		} catch (RuntimeException e) {
			log.error("Review with hotel id :{} could not be onboarded.", hotelId, e);
		}

	}

}
