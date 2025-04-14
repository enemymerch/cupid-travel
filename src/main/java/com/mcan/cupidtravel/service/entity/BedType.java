package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BedType implements Serializable {
	private int quantity;
	private String bedType;
	private String bedSize;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bedSize == null) ? 0 : bedSize.hashCode());
		result = prime * result + ((bedType == null) ? 0 : bedType.hashCode());
		result = prime * result + quantity;
		return result;
	}
}
