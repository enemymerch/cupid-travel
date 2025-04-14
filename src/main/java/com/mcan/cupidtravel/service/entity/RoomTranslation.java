package com.mcan.cupidtravel.service.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoomTranslation implements Serializable {
	private String lang;
	private String roomName;
	private String description;

}
