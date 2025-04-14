package com.mcan.cupidtravel.mapper;

import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.FacilityTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityTranslationJpaEntity;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.FacilityTranslation;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.service.entity.RoomAmenityTranslation;

public interface HotelMapper {
	Hotel toHotel(HotelJpaEntity hotelJpaEntity);

	HotelJpaEntity toHotelJpaEntity(Hotel hotel);

	FacilityJpaEntity toFacilityJpaEntity(Facility facility);

	FacilityTranslationJpaEntity toFacilityTranslationJpaEntity(FacilityTranslation facilityTranslation);

	Facility toFacility(FacilityJpaEntity facilityJpaEntity);

	Hotel toDetailedHotel(HotelJpaEntity hotelJpaEntity);

	RoomAmenityTranslationJpaEntity toRoomAmenityTranslationJpaEntity(RoomAmenityTranslation translation);

	RoomAmenity toRoomAmenity(RoomAmenityJpaEntity jpaEntity);

	RoomAmenityJpaEntity toAmenityJpaEntity(RoomAmenity roomAmenity);

}
