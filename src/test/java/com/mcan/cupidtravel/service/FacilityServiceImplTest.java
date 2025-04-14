package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.mapper.HotelMapper;
import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.FacilityTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.repository.FacilityRepository;
import com.mcan.cupidtravel.persistence.repository.FacilityTranslationRepository;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.FacilityTranslation;
import com.mcan.cupidtravel.service.impl.FacilityServiceImpl;
import com.mcan.cupidtravel.service.impl.IdGenerator;
import com.mcan.cupidtravel.utils.constant.ErrorMessages;
import com.mcan.cupidtravel.utils.exception.FacilityPersistenceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FacilityServiceImplTest {

	@Mock
	private FacilityRepository facilityRepository;

	@Mock
	private FacilityTranslationRepository facilityTranslationRepository;

	@Mock
	private HotelMapper hotelMapper;

	@Mock
	private IdGenerator idGenerator;

	@InjectMocks
	private FacilityServiceImpl facilityService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreate() {
		Facility facility = new Facility();
		facility.setFacility("Gym");

		FacilityJpaEntity facilityJpaEntity = new FacilityJpaEntity();
		facilityJpaEntity.setFacilityId(1L);
		FacilityTranslationJpaEntity facilityTranslationJpaEntity = new FacilityTranslationJpaEntity();
		facilityTranslationJpaEntity.setLang("en");
		facilityTranslationJpaEntity.setFacility("Gym");
		facilityJpaEntity.setTranslation(Collections.singletonList(facilityTranslationJpaEntity));

		when(hotelMapper.toFacilityJpaEntity(facility)).thenReturn(facilityJpaEntity);
		when(facilityRepository.save(facilityJpaEntity)).thenReturn(facilityJpaEntity);
		when(hotelMapper.toFacility(facilityJpaEntity)).thenReturn(facility);

		Facility createdFacility = facilityService.create(facility);

		assertNotNull(createdFacility);
		assertEquals("Gym", createdFacility.getFacility());

		verify(hotelMapper).toFacilityJpaEntity(facility);
		verify(facilityRepository).save(facilityJpaEntity);
		verify(hotelMapper).toFacility(facilityJpaEntity);
	}

	@Test
	void testUpdate() {
		Facility facility = new Facility();
		facility.setFacilityId(1L);
		facility.setFacility("Gym");
		FacilityTranslation facilityTranslation = new FacilityTranslation();
		facilityTranslation.setFacility("Gym");
		facilityTranslation.setLang("en");
		facility.setTranslation(Collections.singletonList(facilityTranslation));

		FacilityJpaEntity existingFacility = new FacilityJpaEntity();
		existingFacility.setFacilityId(1L);
		FacilityTranslationJpaEntity facilityTranslationJpaEntity = new FacilityTranslationJpaEntity();
		facilityTranslationJpaEntity.setLang("en");
		facilityTranslationJpaEntity.setFacility("Gym");
		facilityTranslationJpaEntity.setFacilityTranslationId(1L);
		existingFacility.setTranslation(Collections.singletonList(facilityTranslationJpaEntity));

		when(facilityRepository.findById(1L)).thenReturn(Optional.of(existingFacility));
		when(hotelMapper.toFacilityTranslationJpaEntity(facilityTranslation)).thenReturn(facilityTranslationJpaEntity);

		facilityService.update(1L, facility);

		verify(facilityRepository).findById(1L);
		verify(facilityRepository).save(existingFacility);
	}

	@Test
	void testExistsById() {
		when(facilityRepository.existsById(1L)).thenReturn(true);

		boolean exists = facilityService.existsById(1L);

		assertTrue(exists);
		verify(facilityRepository).existsById(1L);
	}

	@Test
	void testGetAll() {
		FacilityJpaEntity facilityJpaEntity = new FacilityJpaEntity();
		facilityJpaEntity.setFacilityId(1L);

		when(facilityRepository.findAll()).thenReturn(Collections.singletonList(facilityJpaEntity));
		when(hotelMapper.toFacility(facilityJpaEntity)).thenReturn(new Facility());

		List<Facility> facilities = facilityService.getAll();

		assertNotNull(facilities);
		assertEquals(1, facilities.size());

		verify(facilityRepository).findAll();
		verify(hotelMapper).toFacility(facilityJpaEntity);
	}

	@Test
	void testGetAllFacilityIds() {
		List<Long> facilityIds = Collections.singletonList(1L);

		when(facilityRepository.getAllFacilityIds()).thenReturn(facilityIds);

		List<Long> result = facilityService.getAllFacilityIds();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0));

		verify(facilityRepository).getAllFacilityIds();
	}

	@Test
	void testDeleteAll() {
		List<Long> idsToDelete = Collections.singletonList(1L);

		facilityService.deleteAll(idsToDelete);

		verify(facilityRepository).deleteAllById(idsToDelete);
	}

	@Test
	void testGetById() {
		FacilityJpaEntity facilityJpaEntity = new FacilityJpaEntity();
		facilityJpaEntity.setFacilityId(1L);

		when(facilityRepository.findById(1L)).thenReturn(Optional.of(facilityJpaEntity));
		when(hotelMapper.toFacility(facilityJpaEntity)).thenReturn(new Facility());

		Facility facility = facilityService.getById(1L);

		assertNotNull(facility);
		verify(facilityRepository).findById(1L);
		verify(hotelMapper).toFacility(facilityJpaEntity);
	}

	@Test
	void testValidateFacilityForSave() {
		Facility facility = new Facility();
		facility.setFacility("Gym");
		facility.setFacilityId(1L);

		facilityService.create(facility);

		ArgumentCaptor<Facility> captor = ArgumentCaptor.forClass(Facility.class);
		verify(hotelMapper).toFacilityJpaEntity(captor.capture());

		Facility capturedFacility = captor.getValue();
		assertNotNull(capturedFacility);
		assertEquals("Gym", capturedFacility.getFacility());
	}

	@Test
	void testValidateFacilityForSave_ThrowsException_WhenFacilityIsNull() {
		FacilityPersistenceValidationException exception = assertThrows(
			  FacilityPersistenceValidationException.class,
			  () -> facilityService.create(null)
		);

		assertEquals(ErrorMessages.FACILITY_WITHOUT_DETAILS, exception.getMessage());
	}

	@Test
	void testValidateFacilityForSave_ThrowsException_WhenFacilityNameIsNull() {
		Facility facility = new Facility();

		FacilityPersistenceValidationException exception = assertThrows(
			  FacilityPersistenceValidationException.class,
			  () -> facilityService.create(facility)
		);

		assertEquals(ErrorMessages.FACILITY_WITHOUT_NAME, exception.getMessage());
	}

}