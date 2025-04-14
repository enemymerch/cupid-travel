package com.mcan.cupidtravel.persistence.repository;

import com.mcan.cupidtravel.persistence.entity.ReviewJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewJpaEntity, Long> {
	List<ReviewJpaEntity> getAllByHotelId(Long hotelId);
}
