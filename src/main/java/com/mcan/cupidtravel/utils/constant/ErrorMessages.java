package com.mcan.cupidtravel.utils.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {
	public static final String HOTEL_WITHOUT_TYPE = "Cannot save hotel without type.";
	public static final String HOTEL_NOT_FOUND_EXCEPTION = "Hotel not found with identifier: ";
	public static final String HOTEL_WITHOUT_CUPID_ID = "Cannot save hotel without cupid id.";
	public static final String HOTEL_WITHOUT_DETAILS = "Cannot save hotel without any details.";
	public static final String FACILITY_WITHOUT_DETAILS = "Cannot save facility without any details.";
	public static final String FACILITY_WITHOUT_NAME = "Cannot save facility without name.";
	public static final String FACILITY_NOT_FOUND_EXCEPTION = "Facility not found with identifier: ";
	public static final String AMENITY_NOT_FOUND_EXCEPTION = "Amenity not found with identifier: ";
	public static final String AMENITY_WITHOUT_DETAILS = "Cannot save amenity without any details.";


}
