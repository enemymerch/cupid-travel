package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.service.entity.RoomAmenity;

import java.util.List;

public interface RoomAmenityService {
    boolean existsById(Long amenityId);


    void update(Long amenityId, RoomAmenity roomAmenity);

    RoomAmenity create(RoomAmenity roomAmenity);

    List<Long> getAllAmenityIds();

    void deleteAll(List<Long> tobeDeletedIds);

    RoomAmenity getById(Long amenityId);

}
