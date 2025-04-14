package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.service.entity.Review;

import java.util.List;

public interface ReviewService {

	Review create(Review review);

	List<Review> getAllReviewsByHotelId(Long hotelId);

	void deleteAll(List<Review> tobeDeletedReviews);

	List<Review> createAll(List<Review> tobeCreatedReviews);


}
