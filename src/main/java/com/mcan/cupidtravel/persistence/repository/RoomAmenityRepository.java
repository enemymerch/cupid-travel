package com.mcan.cupidtravel.persistence.repository;

import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomAmenityRepository extends JpaRepository<RoomAmenityJpaEntity, Long> {
	@Query("SELECT r.amenityId FROM RoomAmenityJpaEntity r")
	List<Long> getAllAmenityIds();

}
