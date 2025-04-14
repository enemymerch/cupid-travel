package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class Room implements Serializable {

	private Long id;
	private String roomName;
	private String description;
	private int roomSizeSquare;
	private String roomSizeUnit;
	private String hotelId;
	private int maxAdults;
	private int maxChildren;
	private int maxOccupancy;
	private List<BedType> bedTypes;
	private List<RoomAmenity> roomAmenities;
	private List<Photo> photos;
	private List<RoomTranslation> translations;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Room room = (Room) o;
		return roomSizeSquare == room.roomSizeSquare &&
			  maxAdults == room.maxAdults &&
			  maxChildren == room.maxChildren &&
			  maxOccupancy == room.maxOccupancy &&
			  id.equals(room.id) &&
			  roomName.equals(room.roomName) &&
			  description.equals(room.description) &&
			  roomSizeUnit.equals(room.roomSizeUnit) &&
			  hotelId.equals(room.hotelId) &&
			  bedTypes.equals(room.bedTypes) &&
			  photos.equals(room.photos) &&
			  translations.equals(room.translations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, roomName, description, roomSizeSquare, roomSizeUnit, hotelId, maxAdults, maxChildren, maxOccupancy, bedTypes, photos, translations);
	}


}
