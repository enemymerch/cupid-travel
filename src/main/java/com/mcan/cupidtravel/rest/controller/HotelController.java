package com.mcan.cupidtravel.rest.controller;

import com.mcan.cupidtravel.rest.request.HotelSearchRequest;
import com.mcan.cupidtravel.service.HotelService;
import com.mcan.cupidtravel.service.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelController {
	private static final Logger log = LoggerFactory.getLogger(HotelController.class);

	private final HotelService hotelService;

	@GetMapping(value = "/hotel/{hotelId}")
	public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
		log.info("GET /hotel/{} triggered", hotelId);
		Hotel hotel = hotelService.getById(hotelId);
		return ResponseEntity.ok(hotel);
	}

	@GetMapping("/hotel/{hotelId}/detailed")
	public ResponseEntity<Hotel> getDetailedHotelById(@PathVariable Long hotelId) {
		log.info("GET /hotel/{}}/detailed triggered", hotelId);
		Hotel hotel = hotelService.getDetailedById(hotelId);
		return ResponseEntity.ok(hotel);
	}

	@PostMapping("/hotel/filter")
	public ResponseEntity<Page<Hotel>> filterHotelById(@RequestBody HotelSearchRequest request) {
		log.info("POST /hotel/filter triggered with request: {}", request);
		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), sort);
		return ResponseEntity.ok(hotelService.search(pageRequest, request));
	}

	@PostMapping("/hotel")
	public ResponseEntity<Hotel> create(@RequestBody Hotel hotel) {
		log.info("POST /hotel triggered with request: {}", hotel);
		return ResponseEntity.ok(hotelService.create(hotel));
	}

	@PutMapping("/hotel/{hotelId}")
	public ResponseEntity<Hotel> update(@PathVariable Long hotelId, @RequestBody Hotel hotel) {
		log.info("PUT /hotel/{} triggered with request: {}", hotelId, hotel);
		return ResponseEntity.ok(hotelService.update(hotel));
	}

}
