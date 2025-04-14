package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Facility implements Serializable {

	private long facilityId;
	private String facility;
	private int sort;
	private List<FacilityTranslation> translation;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Facility o2 = (Facility) o;
		return facilityId == o2.facilityId &&
			  sort == o2.sort &&
			  java.util.Objects.equals(this.facility, o2.facility) &&
			  CollectionUtils.isEqualCollection(this.translation, o2.getTranslation());
	}

	@Override
	public int hashCode() {
		return Objects.hash(facilityId, facility, sort, translation);
	}


}
