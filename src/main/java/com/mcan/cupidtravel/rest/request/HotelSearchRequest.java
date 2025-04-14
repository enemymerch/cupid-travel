package com.mcan.cupidtravel.rest.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class HotelSearchRequest {
	private Long hotelId;
	private Long cupidId;
	private Double rating;
	private String hotelType;
	private String name;
	private String city;
	private String country;
	private int page;
	private int size;
	private String sortBy;
	private Sort.Direction sortDirection;


	public Sort.Direction getSortDirection() {
		if (this.sortDirection == null) {
			this.sortDirection = Sort.Direction.ASC;
		}
		return this.sortDirection;
	}

	public int getSize() {
		if (this.size < 1) {
			this.size = 5;
		}
		return this.size;
	}

	public String getSortBy() {
		if (this.sortBy == null) {
			this.sortBy = "hotelId";
		}
		return this.sortBy;
	}

}
