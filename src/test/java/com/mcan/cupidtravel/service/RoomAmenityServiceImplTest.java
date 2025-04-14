package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.mapper.HotelMapper;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.repository.RoomAmenityRepository;
import com.mcan.cupidtravel.persistence.repository.RoomAmenityTranslationRepository;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.service.entity.RoomAmenityTranslation;
import com.mcan.cupidtravel.service.impl.IdGenerator;
import com.mcan.cupidtravel.service.impl.RoomAmenityServiceImpl;
import com.mcan.cupidtravel.utils.constant.ErrorMessages;
import com.mcan.cupidtravel.utils.exception.AmenityPersistenceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomAmenityServiceImplTest {

	@Mock
	private RoomAmenityRepository roomAmenityRepository;

	@Mock
	private RoomAmenityTranslationRepository roomAmenityTranslationRepository;

	@Mock
	private HotelMapper mapper;

	@Mock
	private IdGenerator idGenerator;

	@InjectMocks
	private RoomAmenityServiceImpl roomAmenityService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreate() {
		RoomAmenity mockRoomAmenity = new RoomAmenity();
		RoomAmenityTranslation translation = new RoomAmenityTranslation();
		translation.setAmenity("amenity");
		mockRoomAmenity.setTranslation(Collections.singletonList(translation));
		RoomAmenityJpaEntity mockRoomAmenityJpaEntity = new RoomAmenityJpaEntity();
		RoomAmenityTranslationJpaEntity translationJpa = new RoomAmenityTranslationJpaEntity();
		translationJpa.setLang("en");
		translationJpa.setAmenity("amenity");
		mockRoomAmenityJpaEntity.setTranslation(Collections.singletonList(translationJpa));

		when(mapper.toAmenityJpaEntity(mockRoomAmenity)).thenReturn(mockRoomAmenityJpaEntity);
		when(roomAmenityRepository.save(mockRoomAmenityJpaEntity)).thenReturn(mockRoomAmenityJpaEntity);
		when(mapper.toRoomAmenity(mockRoomAmenityJpaEntity)).thenReturn(mockRoomAmenity);

		RoomAmenity createdRoomAmenity = roomAmenityService.create(mockRoomAmenity);

		assertNotNull(createdRoomAmenity);
		verify(roomAmenityRepository).save(mockRoomAmenityJpaEntity);
	}

	@Test
	void testUpdate() {
		Long amenityId = 1L;
		RoomAmenity mockRoomAmenity = new RoomAmenity();
		RoomAmenityTranslation translation = new RoomAmenityTranslation();
		translation.setAmenity("amenity");
		mockRoomAmenity.setTranslation(Collections.singletonList(translation));
		RoomAmenityJpaEntity mockRoomAmenityJpaEntity = new RoomAmenityJpaEntity();
		RoomAmenityTranslationJpaEntity translationJpa = new RoomAmenityTranslationJpaEntity();
		translationJpa.setLang("en");
		translationJpa.setAmenity("amenity");
		mockRoomAmenityJpaEntity.setTranslation(Collections.singletonList(translationJpa));

		when(roomAmenityRepository.findById(amenityId)).thenReturn(Optional.of(mockRoomAmenityJpaEntity));
		when(mapper.toRoomAmenity(mockRoomAmenityJpaEntity)).thenReturn(mockRoomAmenity);
		when(mapper.toRoomAmenityTranslationJpaEntity(translation)).thenReturn(translationJpa);
		when(roomAmenityRepository.save(mockRoomAmenityJpaEntity)).thenReturn(mockRoomAmenityJpaEntity);

		roomAmenityService.update(amenityId, mockRoomAmenity);

		verify(roomAmenityRepository).save(mockRoomAmenityJpaEntity);
	}

	@Test
	void testGetAllAmenityIds() {
		List<Long> mockAmenityIds = Collections.singletonList(1L);
		when(roomAmenityRepository.getAllAmenityIds()).thenReturn(mockAmenityIds);

		List<Long> amenityIds = roomAmenityService.getAllAmenityIds();

		assertNotNull(amenityIds);
		assertEquals(1, amenityIds.size());
		assertEquals(mockAmenityIds.get(0), amenityIds.get(0));
		verify(roomAmenityRepository).getAllAmenityIds();
	}

	@Test
	void testDeleteAll() {
		List<Long> toBeDeletedIds = Collections.singletonList(1L);

		roomAmenityService.deleteAll(toBeDeletedIds);

		verify(roomAmenityRepository).deleteAllById(toBeDeletedIds);
	}

	@Test
	void testGetById() {
		Long amenityId = 1L;
		RoomAmenityJpaEntity mockRoomAmenityJpaEntity = new RoomAmenityJpaEntity();
		when(roomAmenityRepository.findById(amenityId)).thenReturn(Optional.of(mockRoomAmenityJpaEntity));
		RoomAmenity mockRoomAmenity = new RoomAmenity();
		when(mapper.toRoomAmenity(mockRoomAmenityJpaEntity)).thenReturn(mockRoomAmenity);

		RoomAmenity roomAmenity = roomAmenityService.getById(amenityId);

		assertNotNull(roomAmenity);
		assertEquals(mockRoomAmenity, roomAmenity);
		verify(roomAmenityRepository).findById(amenityId);
	}

	@Test
	void testExistsById() {
		Long amenityId = 1L;
		when(roomAmenityRepository.existsById(amenityId)).thenReturn(true);

		boolean exists = roomAmenityService.existsById(amenityId);

		assertTrue(exists);
		verify(roomAmenityRepository).existsById(amenityId);
	}

	@Test
	void testValidateAmenityForSave_NullAmenity() {
		AmenityPersistenceValidationException exception = assertThrows(AmenityPersistenceValidationException.class, () -> {
			roomAmenityService.create(null);
		});
		assertEquals(ErrorMessages.AMENITY_WITHOUT_DETAILS, exception.getMessage());
	}

}