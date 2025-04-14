package com.mcan.cupidtravel.rest.client;

import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.Review;
import com.mcan.cupidtravel.service.entity.RoomAmenity;

import java.util.List;

public interface CupidClient {

	Hotel getHotelContent(Long hotelId);

	Hotel getHotelTranslation(Long hotelId, String lang);

	List<Review> getReviewByHotelId(Long hotelId);

	List<Facility> getAllFacilities();

	List<RoomAmenity> getAllAmenities();

}
