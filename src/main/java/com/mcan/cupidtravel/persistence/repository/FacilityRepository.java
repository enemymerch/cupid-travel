package com.mcan.cupidtravel.persistence.repository;

import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilityRepository extends JpaRepository<FacilityJpaEntity, Long> {
	@Query("SELECT f.facilityId FROM FacilityJpaEntity f")
	List<Long> getAllFacilityIds();

}
