package com.mcan.cupidtravel.persistence.repository;

import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRepository extends JpaRepository<HotelJpaEntity, Long>, JpaSpecificationExecutor<HotelJpaEntity> {
}
