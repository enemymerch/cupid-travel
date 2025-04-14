package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.persistence.entity.ReviewJpaEntity;
import com.mcan.cupidtravel.persistence.repository.ReviewRepository;
import com.mcan.cupidtravel.service.ReviewService;
import com.mcan.cupidtravel.service.entity.Review;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

	private final ReviewRepository reviewRepository;

	@Override
	public Review create(Review review) {
		if (review == null) return null;
		ReviewJpaEntity reviewJpaEntity = toReviewJpaEntity(review);
		return toReview(reviewRepository.save(reviewJpaEntity));
	}

	@Override
	public List<Review> getAllReviewsByHotelId(Long hotelId) {
		return reviewRepository.getAllByHotelId(hotelId).stream().map(this::toReview).toList();
	}

	@Override
	public void deleteAll(List<Review> tobeDeletedReviews) {
		reviewRepository.deleteAll(tobeDeletedReviews.stream().map(this::toReviewJpaEntity).toList());
	}

	@Override
	public List<Review> createAll(List<Review> tobeCreatedReviews) {
		return tobeCreatedReviews.stream().map(this::create).toList();
	}

	private Review toReview(ReviewJpaEntity reviewJpaEntity) {
		if (reviewJpaEntity == null) return null;
		Review review = new Review();
		review.setCons(reviewJpaEntity.getCons());
		review.setAverageScore(reviewJpaEntity.getAverageScore());
		review.setCountry(reviewJpaEntity.getCountry());
		review.setDate(reviewJpaEntity.getDate());
		review.setHeadline(reviewJpaEntity.getHeadline());
		review.setLanguage(reviewJpaEntity.getLanguage());
		review.setName(reviewJpaEntity.getName());
		review.setPros(reviewJpaEntity.getPros());
		review.setSource(reviewJpaEntity.getSource());
		review.setType(reviewJpaEntity.getType());
		review.setHotelId(reviewJpaEntity.getHotelId());
		return review;
	}


	private ReviewJpaEntity toReviewJpaEntity(Review review) {
		if (review == null) return null;
		ReviewJpaEntity reviewJpaEntity = new ReviewJpaEntity();
		reviewJpaEntity.setReviewId((long) review.hashCode());
		reviewJpaEntity.setCons(review.getCons());
		reviewJpaEntity.setAverageScore(review.getAverageScore());
		reviewJpaEntity.setCountry(review.getCountry());
		reviewJpaEntity.setDate(review.getDate());
		reviewJpaEntity.setHeadline(review.getHeadline());
		reviewJpaEntity.setLanguage(review.getLanguage());
		reviewJpaEntity.setName(review.getName());
		reviewJpaEntity.setPros(review.getPros());
		reviewJpaEntity.setSource(review.getSource());
		reviewJpaEntity.setType(review.getType());
		reviewJpaEntity.setHotelId(review.getHotelId());
		return reviewJpaEntity;
	}

}
