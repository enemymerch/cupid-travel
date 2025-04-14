package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class RoomAmenity implements Serializable {

	@JsonAlias({"amenity_id", "amenities_id"})
	private Long amenityId;
	private String amenity;
	private int sort;
	private List<RoomAmenityTranslation> translation;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoomAmenity that = (RoomAmenity) o;
		return amenityId.equals(that.amenityId) &&
			  sort == that.sort &&
			  Objects.equals(amenity, that.amenity) &&
			  CollectionUtils.isEqualCollection(this.translation, that.getTranslation());
	}

	@Override
	public int hashCode() {
		return Objects.hash(amenityId, amenity, sort, translation);
	}

}
