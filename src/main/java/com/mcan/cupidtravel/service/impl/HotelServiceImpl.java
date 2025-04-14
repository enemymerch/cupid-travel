package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.mapper.HotelMapper;
import com.mcan.cupidtravel.persistence.entity.AddressJpaEntity;
import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import com.mcan.cupidtravel.persistence.repository.FacilityRepository;
import com.mcan.cupidtravel.persistence.repository.HotelRepository;
import com.mcan.cupidtravel.persistence.repository.RoomAmenityRepository;
import com.mcan.cupidtravel.rest.request.HotelSearchRequest;
import com.mcan.cupidtravel.service.HotelService;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.utils.JpaSpecificationUtils;
import com.mcan.cupidtravel.utils.constant.ErrorMessages;
import com.mcan.cupidtravel.utils.exception.HotelNotFoundException;
import com.mcan.cupidtravel.utils.exception.HotelPersistenceValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

	private static final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

	private final HotelRepository hotelRepository;
	private final IdGenerator idGenerator;
	private final RoomAmenityRepository roomAmenityRepository;
	private final HotelMapper hotelMapper;
	private final FacilityRepository facilityRepository;

	@Override
	@Cacheable(value = "hotels", key = "#hotel.hotelId")
	public Hotel create(Hotel hotel) {
		validateHotelForSave(hotel);
		HotelJpaEntity jpaEntity = hotelMapper.toHotelJpaEntity(hotel);
		setupJpaRelations(jpaEntity);
		jpaEntity = hotelRepository.save(jpaEntity);
		return hotelMapper.toHotel(jpaEntity);
	}

	private void setupJpaRelations(HotelJpaEntity jpaEntity) {
		if (jpaEntity.getHotelId() == null) {
			jpaEntity.setHotelId(idGenerator.generateId());
		}
		if (jpaEntity.getAddress() != null) {
			jpaEntity.getAddress().setHotel(jpaEntity);
		}
		if (jpaEntity.getTranslation() != null) {
			jpaEntity.getTranslation().forEach(hotelTranslationJpaEntity -> {
				if (hotelTranslationJpaEntity.getHotelTranslationId() == null) hotelTranslationJpaEntity.setHotelTranslationId(idGenerator.generateId());
				hotelTranslationJpaEntity.setHotelId(jpaEntity.getHotelId());
			});
		}
		if (jpaEntity.getPhotos() != null) {
			jpaEntity.getPhotos().forEach(photoJpaEntity -> {
				if (photoJpaEntity.getPhotoId() == null) photoJpaEntity.setPhotoId(idGenerator.generateId());
				photoJpaEntity.setHotel(jpaEntity);
				photoJpaEntity.setHotelId(jpaEntity.getHotelId());
			});
		}
		if (jpaEntity.getPolicies() != null) {
			jpaEntity.getPolicies().forEach(policyJpaEntity -> {
				if (policyJpaEntity.getPolicyId() == null) policyJpaEntity.setPolicyId(idGenerator.generateId());
				policyJpaEntity.setHotel(jpaEntity);
				policyJpaEntity.setHotelId(jpaEntity.getHotelId());
			});
		}
		if (jpaEntity.getFacilities() != null) {
			List<FacilityJpaEntity> facilityJpaEntities = facilityRepository.findAllById(jpaEntity.getFacilities().stream().map(FacilityJpaEntity::getFacilityId).toList());
			jpaEntity.setFacilities(facilityJpaEntities);
		}
		if (jpaEntity.getRooms() != null) {
			jpaEntity.getRooms().forEach(roomJpaEntity -> {
				if (roomJpaEntity.getRoomId() == null) roomJpaEntity.setRoomId(idGenerator.generateId());
				roomJpaEntity.setHotel(jpaEntity);
				roomJpaEntity.setHotelId(jpaEntity.getHotelId());
				if (roomJpaEntity.getAmenities() != null) {
					List<Long> amenityIds = roomJpaEntity.getAmenities().stream().map(RoomAmenityJpaEntity::getAmenityId).toList();
					roomJpaEntity.setAmenities(roomAmenityRepository.findAllById(amenityIds));
				}
				if (roomJpaEntity.getTranslation() != null) {
					roomJpaEntity.getTranslation().forEach(roomTranslationJpaEntity -> {
						if (roomTranslationJpaEntity.getRoomTranslationId() == null) roomTranslationJpaEntity.setRoomTranslationId(idGenerator.generateId());
						roomTranslationJpaEntity.setRoomId(roomJpaEntity.getRoomId());
						roomTranslationJpaEntity.setRoom(roomJpaEntity);
					});
				}
				if (roomJpaEntity.getPhotos() != null) {
					roomJpaEntity.getPhotos().forEach(photoJpaEntity -> {
						if (photoJpaEntity.getPhotoId() == null) photoJpaEntity.setPhotoId(idGenerator.generateId());
						photoJpaEntity.setRoom(roomJpaEntity);
						photoJpaEntity.setRoomId(roomJpaEntity.getRoomId());
					});
				}
			});
		}
	}


	private void validateHotelForSave(Hotel hotel) {
		if (hotel == null) throw new HotelPersistenceValidationException(ErrorMessages.HOTEL_WITHOUT_DETAILS);
		if (hotel.getCupidId() == null) throw new HotelPersistenceValidationException(ErrorMessages.HOTEL_WITHOUT_CUPID_ID);
		if (hotel.getHotelTypeId() == null) throw new HotelPersistenceValidationException(ErrorMessages.HOTEL_WITHOUT_TYPE);
	}

	@Override
	@Caching(evict = {
		  @CacheEvict(value = "hotels", key = "#hotel.getHotelId()"),
		  @CacheEvict(value = "hotelsDetails", key = "#hotel.getHotelId()")
	},
		  put = {
				@CachePut(value = "hotels", key = "#hotel.getHotelId()")
		  })
	public Hotel update(Hotel hotel) {
		if (!exists(hotel)) {
			log.info("Hotel with id :{} does not exist.", hotel.getHotelId());
			throw new HotelNotFoundException(hotel.getHotelId());
		}
		validateHotelForSave(hotel);
		hotelRepository.deleteById(hotel.getHotelId());
		HotelJpaEntity hotelJpaEntity = hotelMapper.toHotelJpaEntity(hotel);
		setupJpaRelations(hotelJpaEntity);
		return hotelMapper.toHotel(hotelRepository.save(hotelJpaEntity));
	}

	@Override
	public boolean existsById(Long hotelId) {
		return hotelRepository.existsById(hotelId);
	}

	@Override
	@Cacheable(value = "hotels", key = "#hotelId")
	public Hotel getById(Long hotelId) {
		HotelJpaEntity hotelJpaEntity = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException(hotelId));
		return hotelMapper.toHotel(hotelJpaEntity);
	}

	@Override
	@Cacheable(value = "hotelsDetails", key = "#hotelId")
	public Hotel getDetailedById(Long hotelId) {
		HotelJpaEntity hotelJpaEntity = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException(hotelId));
		return hotelMapper.toDetailedHotel(hotelJpaEntity);
	}

	@Override
	public Page<Hotel> search(PageRequest pageRequest, HotelSearchRequest request) {
		if (request == null || pageRequest == null) return null;
		Specification<HotelJpaEntity> hotelSearcSpecification = JpaSpecificationUtils.buildFindByEqualsSpecification(HotelJpaEntity.class, "cupidId", request.getCupidId())
																					 .and(JpaSpecificationUtils.buildFindByEqualsSpecification(HotelJpaEntity.class, "hotelId", request.getHotelId()))
																					 .and(JpaSpecificationUtils.buildFindByEqualsOrGreaterSpecification("rating", request.getRating()))
																					 .and(JpaSpecificationUtils.buildFindByJoinLikeSpecification(HotelJpaEntity.class, HotelTranslationJpaEntity.class, "translation", "hotelName",
																																				 request.getName()))
																					 .and(JpaSpecificationUtils.buildFindByJoinLikeSpecification(HotelJpaEntity.class, HotelTranslationJpaEntity.class, "translation", "hotelType",
																																				 request.getHotelType()))
																					 .and(JpaSpecificationUtils.buildFindByJoinLikeSpecification(HotelJpaEntity.class, HotelTranslationJpaEntity.class, "translation", "lang",
																																				 LocaleContextHolder.getLocale().getLanguage()))
																					 .and(JpaSpecificationUtils.buildFindByJoinLikeSpecification(HotelJpaEntity.class, AddressJpaEntity.class, "address", "city", request.getCity()))
																					 .and(JpaSpecificationUtils.buildFindByJoinLikeSpecification(HotelJpaEntity.class, AddressJpaEntity.class, "address", "country", request.getCountry()));
		return hotelRepository.findAll(hotelSearcSpecification, pageRequest).map(hotelMapper::toHotel);
	}


	public boolean exists(Hotel hotel) {
		return existsById(hotel.getHotelId());
	}

}
