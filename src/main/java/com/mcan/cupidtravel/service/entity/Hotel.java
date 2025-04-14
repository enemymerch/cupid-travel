package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Hotel implements Serializable {
	private Long hotelId;
	private Long cupidId;
	private String mainImageTh;
	private String hotelType;
	private Long hotelTypeId;
	private String chain;
	private long chainId;
	private double latitude;
	private double longitude;
	private String hotelName ;
	private String phone;
	private String fax;
	private String email;
	private Address address;
	private int stars;
	private String airportCode;
	private double rating;
	private int reviewCount;
	private String checkinStart;
	private String checkinEnd;
	private String checkout;
	private String parking;
	private int groupRoomMin;
	private boolean childAllowed;
	private boolean petsAllowed;
	private List<Photo> photos;
	private String description;
	private String markdownDescription;
	private String importantInfo;
	private List<Facility> facilities;
	private List<Policy> policies;
	private List<Room> rooms;
	private List<HotelTranslation> translation;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hotel that = (Hotel) o;
		return Objects.equals(hotelId, that.hotelId) &&
			  Objects.equals(cupidId, that.cupidId) &&
			  latitude == that.latitude &&
			  longitude == that.longitude &&
			  stars == that.stars &&
			  rating == that.rating &&
			  reviewCount == that.reviewCount &&
			  groupRoomMin == that.groupRoomMin &&
			  childAllowed == that.childAllowed &&
			  petsAllowed == that.petsAllowed &&
			  Objects.equals(hotelTypeId, that.hotelTypeId) &&
			  chainId == that.chainId &&
			  Objects.equals(mainImageTh, that.mainImageTh) &&
			  Objects.equals(hotelType, that.hotelType) &&
			  Objects.equals(chain, that.chain) &&
			  Objects.equals(hotelName, that.hotelName) &&
			  Objects.equals(phone, that.phone) &&
			  Objects.equals(fax, that.fax) &&
			  Objects.equals(email, that.email) &&
			  Objects.equals(address, that.address) &&
			  Objects.equals(airportCode, that.airportCode) &&
			  Objects.equals(checkinStart, that.checkinStart) &&
			  Objects.equals(checkinEnd, that.checkinEnd) &&
			  Objects.equals(checkout, that.checkout) &&
			  Objects.equals(parking, that.parking) &&
			  Objects.equals(description, that.description) &&
			  Objects.equals(markdownDescription, that.markdownDescription) &&
			  Objects.equals(importantInfo, that.importantInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hotelId, cupidId, mainImageTh, hotelType, hotelTypeId, chain, chainId, latitude, longitude, hotelName, phone, fax, email, address, stars, airportCode, rating, reviewCount, checkinStart, checkinEnd, checkout,
							parking, groupRoomMin, childAllowed, petsAllowed, description, markdownDescription, importantInfo);
	}

}