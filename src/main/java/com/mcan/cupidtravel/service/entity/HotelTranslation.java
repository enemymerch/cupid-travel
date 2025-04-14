package com.mcan.cupidtravel.service.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HotelTranslation implements Serializable {
	private String lang;
	private String hotelName;
	private String hotelType;
	private String description;
	private String markdownDescription;
	private String importantInfo;

}