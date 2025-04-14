package com.mcan.cupidtravel.service.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class FacilityTranslation implements Serializable {
	private String lang;
	private String facility;
}
