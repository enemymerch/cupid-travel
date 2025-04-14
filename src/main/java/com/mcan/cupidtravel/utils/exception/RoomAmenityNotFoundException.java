package com.mcan.cupidtravel.utils.exception;

import com.mcan.cupidtravel.utils.constant.ErrorMessages;

public class RoomAmenityNotFoundException extends RuntimeException {

	public RoomAmenityNotFoundException(Object identifier) {
		super(ErrorMessages.AMENITY_NOT_FOUND_EXCEPTION + identifier);
	}
}
