package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.persistence.entity.ReviewJpaEntity;
import com.mcan.cupidtravel.persistence.repository.ReviewRepository;
import com.mcan.cupidtravel.service.entity.Review;
import com.mcan.cupidtravel.service.impl.ReviewServiceImpl;
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
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReviewServiceImplTest {

	@Mock
	private ReviewRepository reviewRepository;

	@InjectMocks
	private ReviewServiceImpl reviewService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreate() {
		Review mockReview = new Review();
		mockReview.setHotelId(1L);
		ReviewJpaEntity mockReviewJpaEntity = new ReviewJpaEntity();
		when(reviewRepository.save(any(ReviewJpaEntity.class))).thenReturn(mockReviewJpaEntity);
		when(reviewRepository.save(any(ReviewJpaEntity.class))).thenReturn(mockReviewJpaEntity);

		Review createdReview = reviewService.create(mockReview);

		assertNotNull(createdReview);
		verify(reviewRepository).save(any(ReviewJpaEntity.class));
	}

	@Test
	void testGetAllReviewsByHotelId() {
		Long hotelId = 1L;
		ReviewJpaEntity mockReviewJpaEntity = new ReviewJpaEntity();
		List<ReviewJpaEntity> mockReviewJpaEntities = Collections.singletonList(mockReviewJpaEntity);
		Review mockReview = new Review();
		when(reviewRepository.getAllByHotelId(hotelId)).thenReturn(mockReviewJpaEntities);

		List<Review> reviews = reviewService.getAllReviewsByHotelId(hotelId);

		assertNotNull(reviews);
		assertEquals(1, reviews.size());
		verify(reviewRepository).getAllByHotelId(hotelId);
	}

	@Test
	void testDeleteAll() {
		Review mockReview = new Review();
		mockReview.setHotelId(1L);
		List<Review> reviews = Collections.singletonList(mockReview);

		reviewService.deleteAll(reviews);

		verify(reviewRepository).deleteAll(anyList());
	}

	@Test
	void testCreateAll() {
		Review mockReview = new Review();
		mockReview.setHotelId(1L);
		List<Review> reviews = Collections.singletonList(mockReview);

		List<Review> createdReviews = reviewService.createAll(reviews);

		assertNotNull(createdReviews);
		assertEquals(1, createdReviews.size());

		// Verify that create method is called for each review in the list
		verify(reviewRepository, times(1)).save(any(ReviewJpaEntity.class));
	}

}