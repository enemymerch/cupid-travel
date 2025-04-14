package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.service.entity.Facility;

import java.util.List;

public interface FacilityService {
    Facility create(Facility facility);

    void update(Long facilityId, Facility facility);

    boolean existsById(long facilityId);

    List<Facility> getAll();


    List<Long> getAllFacilityIds();

    void deleteAll(List<Long> tobeDeletedReviews);

    Facility getById(long facilityId);

}
