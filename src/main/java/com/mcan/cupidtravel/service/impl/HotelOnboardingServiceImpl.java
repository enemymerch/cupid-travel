package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.HotelOnboardingService;
import com.mcan.cupidtravel.service.HotelService;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.HotelTranslation;
import com.mcan.cupidtravel.service.entity.Room;
import com.mcan.cupidtravel.service.entity.RoomTranslation;
import com.mcan.cupidtravel.utils.constant.Languages;
import com.mcan.cupidtravel.utils.property.CupidProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
public class HotelOnboardingServiceImpl implements HotelOnboardingService {

	private static final Logger log = LoggerFactory.getLogger(HotelOnboardingServiceImpl.class);

	private final CupidProperties cupidProperties;
	private final HotelService hotelService;
	private final CupidClient cupidClient;
	private final ConcurrentLinkedQueue<Long> hotelDeadLetters = new ConcurrentLinkedQueue<>();


	public void onboardHotels() {
		if (!hotelDeadLetters.isEmpty()) {
			while (!hotelDeadLetters.isEmpty()) {
				Long hotelId = hotelDeadLetters.poll();
				onboardHotelById(hotelId);
			}
		} else {
			// otherwise read from properties
			Arrays.stream(cupidProperties.getHotelIds()).forEach(this::onboardHotelById);
		}
	}

	@Retryable(maxAttempts = 3, recover = "recoverOnboardHotelById")
	public void onboardHotelById(Long hotelId) {
		Hotel hotel = cupidClient.getHotelContent(hotelId);
		if (hotel == null) {
			return;
		}
		Hotel hotelFrTranslation = cupidClient.getHotelTranslation(hotelId, Languages.FRENCH);
		Hotel hotelEsTranslation = cupidClient.getHotelTranslation(hotelId, Languages.SPANISH);

		buildHotelTranslations(hotel, hotelFrTranslation, hotelEsTranslation);
		buildHotelRoomTranslations(hotel, hotelFrTranslation, hotelEsTranslation);


		onboardHotel(hotel);
	}

	@Recover
	public void recoverOnboardHotelById(Exception e, Long hotelId) {
		hotelDeadLetters.add(hotelId);
		log.error("Hotel with id :{} could not be onboarded.", hotelId, e);
	}

	private void buildHotelRoomTranslations(Hotel hotel, Hotel hotelFrTranslation, Hotel hotelEsTranslation) {
		hotel.getRooms().forEach(room -> {
			List<RoomTranslation> roomTranslations = new ArrayList<>();
			RoomTranslation englishTranslation = buildRoomTranslation(room, Languages.ENGLISH);
			roomTranslations.add(englishTranslation);
			Room roomFrTranslation = findRoom(room.getId(), hotelFrTranslation);
			Room roomEsTranslation = findRoom(room.getId(), hotelEsTranslation);
			if (roomFrTranslation != null) roomTranslations.add(buildRoomTranslation(roomFrTranslation, Languages.FRENCH));
			if (roomEsTranslation != null) roomTranslations.add(buildRoomTranslation(roomEsTranslation, Languages.SPANISH));
			room.setTranslations(roomTranslations);
		});
	}

	private RoomTranslation buildRoomTranslation(Room room, String lang) {
		if (room == null || lang == null) return null;
		RoomTranslation roomTranslation = new RoomTranslation();
		roomTranslation.setLang(lang);
		roomTranslation.setDescription(room.getDescription());
		roomTranslation.setRoomName(room.getRoomName());
		return roomTranslation;
	}

	private Room findRoom(Long roomId, Hotel hotel) {
		if (hotel == null || hotel.getRooms() == null) return null;
		return hotel.getRooms().stream().filter(room -> room.getId().equals(roomId)).findFirst().orElse(null);
	}

	private void buildHotelTranslations(Hotel hotel, Hotel hotelFrTranslation, Hotel hotelEsTranslation) {
		List<HotelTranslation> translations = new ArrayList<>();
		translations.add(buildTranslationEntity(hotel, Languages.ENGLISH));
		translations.add(buildTranslationEntity(hotelFrTranslation, Languages.FRENCH));
		translations.add(buildTranslationEntity(hotelEsTranslation, Languages.SPANISH));
		hotel.setTranslation(translations);
	}

	private HotelTranslation buildTranslationEntity(Hotel hotel, String lang) {
		HotelTranslation hotelTranslation = new HotelTranslation();
		hotelTranslation.setLang(lang);
		hotelTranslation.setHotelName(hotel.getHotelName());
		hotelTranslation.setDescription(hotel.getDescription());
		hotelTranslation.setImportantInfo(hotel.getImportantInfo());
		hotelTranslation.setMarkdownDescription(hotel.getMarkdownDescription());
		hotelTranslation.setHotelType(hotel.getHotelType());
		return hotelTranslation;
	}

	private void onboardHotel(Hotel hotel) {
		if (hotelService.existsById(hotel.getHotelId())) {
			// update
			Hotel existingHotel = hotelService.getDetailedById(hotel.getHotelId());
			if (!existingHotel.equals(hotel)) {
				hotelService.update(hotel);
			}
		} else {
			hotelService.create(hotel);
		}
	}


}
