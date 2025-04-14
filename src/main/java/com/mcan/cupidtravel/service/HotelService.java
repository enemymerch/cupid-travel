package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.rest.request.HotelSearchRequest;
import com.mcan.cupidtravel.service.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface HotelService {

	Hotel create(Hotel hotel);

	Hotel update(Hotel hotel);

	boolean existsById(Long hotelId);

	Hotel getById(Long hotelId);

	Hotel getDetailedById(Long hotelId);

	Page<Hotel> search(PageRequest pageRequest, HotelSearchRequest request);

}
