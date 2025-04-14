package com.mcan.cupidtravel.utils.exception;

import com.mcan.cupidtravel.utils.constant.ErrorMessages;

public class FacilityNotFoundException extends RuntimeException {
	public FacilityNotFoundException(Object facilityIdentifier) {
		super(ErrorMessages.FACILITY_NOT_FOUND_EXCEPTION + facilityIdentifier);
	}

}
