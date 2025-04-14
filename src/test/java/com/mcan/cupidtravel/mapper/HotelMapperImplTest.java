package com.mcan.cupidtravel.mapper;

import com.mcan.cupidtravel.mapper.impl.HotelMapperImpl;
import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.FacilityTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomTranslationJpaEntity;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.RoomTranslation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HotelMapperImplTest {

	@InjectMocks
	private HotelMapperImpl hotelMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testToHotel() {
		HotelJpaEntity hotelJpaEntity = new HotelJpaEntity();
		hotelJpaEntity.setHotelId(1L);
		hotelJpaEntity.setMainImageTh("image.jpg");

		Hotel hotel = hotelMapper.toHotel(hotelJpaEntity);

		assertNotNull(hotel);
		assertEquals(1L, hotel.getHotelId());
		assertEquals("image.jpg", hotel.getMainImageTh());
	}

	@Test
	void testToDetailedHotel() {
		HotelJpaEntity hotelJpaEntity = new HotelJpaEntity();
		hotelJpaEntity.setHotelId(1L);
		hotelJpaEntity.setMainImageTh("image.jpg");

		RoomJpaEntity roomJpaEntity = new RoomJpaEntity();
		roomJpaEntity.setRoomId(1L);
		roomJpaEntity.setRoomSizeSquare(30);
		roomJpaEntity.setMaxAdults(2);
		hotelJpaEntity.setRooms(Collections.singletonList(roomJpaEntity));

		FacilityJpaEntity facilityJpaEntity = new FacilityJpaEntity();
		facilityJpaEntity.setFacilityId(1L);
		facilityJpaEntity.setSort(10);
		FacilityTranslationJpaEntity facilityTranslationJpaEntity = new FacilityTranslationJpaEntity();
		facilityTranslationJpaEntity.setFacilityTranslationId(1L);
		facilityTranslationJpaEntity.setLang("en");
		facilityTranslationJpaEntity.setFacility("Facility");
		facilityJpaEntity.setTranslation(Collections.singletonList(facilityTranslationJpaEntity));
		hotelJpaEntity.setFacilities(Collections.singletonList(facilityJpaEntity));

		Hotel hotel = hotelMapper.toDetailedHotel(hotelJpaEntity);

		assertNotNull(hotel);
		assertEquals(1L, hotel.getHotelId());
		assertEquals("image.jpg", hotel.getMainImageTh());

		assertNotNull(hotel.getRooms());
		assertEquals(1, hotel.getRooms().size());
		assertEquals(1L, hotel.getRooms().get(0).getId());
		assertEquals(30, hotel.getRooms().get(0).getRoomSizeSquare());
		assertEquals(2, hotel.getRooms().get(0).getMaxAdults());

		assertNotNull(hotel.getFacilities());
		assertEquals(1, hotel.getFacilities().size());
		assertEquals(1L, hotel.getFacilities().get(0).getFacilityId());
		assertEquals(10, hotel.getFacilities().get(0).getSort());
	}

	@Test
	void testToRoomTranslations() {
		RoomTranslationJpaEntity roomTranslationJpaEntity = new RoomTranslationJpaEntity();
		roomTranslationJpaEntity.setRoomTranslationId(1L);
		roomTranslationJpaEntity.setLang("en");
		roomTranslationJpaEntity.setRoomName("Deluxe Room");
		roomTranslationJpaEntity.setDescription("A spacious room with a view.");

		List<RoomTranslation> roomTranslations = hotelMapper.toRoomTranslations(Collections.singletonList(roomTranslationJpaEntity));

		assertNotNull(roomTranslations);
		assertEquals(1, roomTranslations.size());
		assertEquals("en", roomTranslations.get(0).getLang());
		assertEquals("Deluxe Room", roomTranslations.get(0).getRoomName());
		assertEquals("A spacious room with a view.", roomTranslations.get(0).getDescription());
	}

}