package com.mcan.cupidtravel.service;

import com.mcan.cupidtravel.mapper.HotelMapper;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.repository.HotelRepository;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.impl.HotelServiceImpl;
import com.mcan.cupidtravel.service.impl.IdGenerator;
import com.mcan.cupidtravel.utils.exception.HotelPersistenceValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelServiceImplTest {

	@Mock
	private HotelRepository hotelRepository;

	@Mock
	private HotelMapper hotelMapper;

	@Mock
	private IdGenerator idGenerator;

	@InjectMocks
	private HotelServiceImpl hotelService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateHotel() {
		Hotel mockHotel = new Hotel();
		mockHotel.setHotelId(1L);
		mockHotel.setCupidId(2L);
		mockHotel.setHotelTypeId(3L);

		HotelJpaEntity mockHotelJpaEntity = new HotelJpaEntity();
		when(hotelMapper.toHotelJpaEntity(mockHotel)).thenReturn(mockHotelJpaEntity);
		when(hotelRepository.save(mockHotelJpaEntity)).thenReturn(mockHotelJpaEntity);
		when(hotelMapper.toHotel(mockHotelJpaEntity)).thenReturn(mockHotel);

		Hotel createdHotel = hotelService.create(mockHotel);

		assertNotNull(createdHotel);
		verify(hotelRepository).save(mockHotelJpaEntity);
	}

	@Test
	void testUpdateHotel() {
		Hotel mockHotel = new Hotel();
		mockHotel.setHotelId(1L);
		mockHotel.setCupidId(2L);
		mockHotel.setHotelTypeId(3L);

		when(hotelRepository.existsById(1L)).thenReturn(true);
		HotelJpaEntity mockHotelJpaEntity = new HotelJpaEntity();
		when(hotelMapper.toHotelJpaEntity(mockHotel)).thenReturn(mockHotelJpaEntity);
		when(hotelRepository.save(mockHotelJpaEntity)).thenReturn(mockHotelJpaEntity);
		when(hotelMapper.toHotel(mockHotelJpaEntity)).thenReturn(mockHotel);

		Hotel updatedHotel = hotelService.update(mockHotel);

		assertNotNull(updatedHotel);
		verify(hotelRepository).deleteById(1L);
		verify(hotelRepository).save(mockHotelJpaEntity);
	}

	@Test
	void testGetHotelById() {
		Long hotelId = 1L;
		HotelJpaEntity mockHotelJpaEntity = new HotelJpaEntity();
		when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(mockHotelJpaEntity));
		Hotel mockHotel = new Hotel();
		when(hotelMapper.toHotel(mockHotelJpaEntity)).thenReturn(mockHotel);

		Hotel hotel = hotelService.getById(hotelId);

		assertNotNull(hotel);
		assertEquals(mockHotel, hotel);
		verify(hotelRepository).findById(hotelId);
	}

	@Test
	void testGetDetailedHotelById() {
		Long hotelId = 1L;
		HotelJpaEntity mockHotelJpaEntity = new HotelJpaEntity();
		when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(mockHotelJpaEntity));
		Hotel mockHotel = new Hotel();
		when(hotelMapper.toDetailedHotel(mockHotelJpaEntity)).thenReturn(mockHotel);

		Hotel hotel = hotelService.getDetailedById(hotelId);

		assertNotNull(hotel);
		assertEquals(mockHotel, hotel);
		verify(hotelRepository).findById(hotelId);
	}

	@Test
	void testExistsById() {
		Long hotelId = 1L;
		when(hotelRepository.existsById(hotelId)).thenReturn(true);

		boolean exists = hotelService.existsById(hotelId);

		assertTrue(exists);
		verify(hotelRepository).existsById(hotelId);
	}

	@Test
	void testValidateHotelForSave_NullHotel() {
		assertThrows(HotelPersistenceValidationException.class, () -> hotelService.create(null));
	}

	@Test
	void testValidateHotelForSave_NullCupidId() {
		Hotel mockHotel = new Hotel();
		assertThrows(HotelPersistenceValidationException.class, () -> hotelService.create(mockHotel));
	}

}