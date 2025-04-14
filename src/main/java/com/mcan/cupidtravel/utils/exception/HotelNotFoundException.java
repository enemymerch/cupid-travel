package com.mcan.cupidtravel.utils.exception;

import com.mcan.cupidtravel.utils.constant.ErrorMessages;

public class HotelNotFoundException extends RuntimeException{
	public HotelNotFoundException(Object hotelIdentifier) {
		super(ErrorMessages.HOTEL_NOT_FOUND_EXCEPTION + hotelIdentifier);
	}

}
