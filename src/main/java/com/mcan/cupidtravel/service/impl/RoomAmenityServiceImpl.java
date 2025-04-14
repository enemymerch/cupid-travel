package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import com.mcan.cupidtravel.persistence.repository.RoomAmenityRepository;
import com.mcan.cupidtravel.persistence.repository.RoomAmenityTranslationRepository;
import com.mcan.cupidtravel.service.RoomAmenityService;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.mapper.HotelMapper;
import com.mcan.cupidtravel.utils.constant.ErrorMessages;
import com.mcan.cupidtravel.utils.exception.AmenityPersistenceValidationException;
import com.mcan.cupidtravel.utils.exception.RoomAmenityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomAmenityServiceImpl implements RoomAmenityService {
	private static final Logger log = LoggerFactory.getLogger(RoomAmenityServiceImpl.class);

	private final RoomAmenityRepository roomAmenityRepository;
	private final RoomAmenityTranslationRepository roomAmenityTranslationRepository;
	private final IdGenerator idGenerator;
	private final HotelMapper mapper;

	@Override
	public boolean existsById(Long amenityId) {
		return roomAmenityRepository.existsById(amenityId);
	}

	@Override
	@Transactional
	public void update(Long amenityId, RoomAmenity roomAmenity) {
		validateAmenityForSave(roomAmenity);
		RoomAmenityJpaEntity existingRoomAmenityJpaEntity = roomAmenityRepository.findById(amenityId).orElseThrow(() -> new RoomAmenityNotFoundException(amenityId));
		existingRoomAmenityJpaEntity.setSort(roomAmenity.getSort());
		log.info("Updated amenity: {}", roomAmenity);
		roomAmenityTranslationRepository.deleteAll(existingRoomAmenityJpaEntity.getTranslation());
		existingRoomAmenityJpaEntity.getTranslation().forEach(roomAmenityTranslationJpaEntity -> {
			roomAmenityTranslationJpaEntity.setRoomAmenity(null);
			roomAmenityTranslationJpaEntity.setAmenityId(null);
		});
		existingRoomAmenityJpaEntity.setTranslation(roomAmenity.getTranslation().stream().map(mapper::toRoomAmenityTranslationJpaEntity).collect(Collectors.toList()));
		setupJpaRelations(existingRoomAmenityJpaEntity);
		roomAmenityRepository.save(existingRoomAmenityJpaEntity);
	}

	private void setupJpaRelations(RoomAmenityJpaEntity entity) {
		if (entity.getAmenityId() == null) {
			entity.setAmenityId(idGenerator.generateId());
		}
		entity.getTranslation().forEach(translation -> {
			if (translation.getAmenityTranslationId() == null) {
				translation.setAmenityTranslationId(idGenerator.generateId());
			}
			translation.setAmenityId(entity.getAmenityId());
		});
	}

	@Override
	@Transactional
	public RoomAmenity create(RoomAmenity roomAmenity) {
		validateAmenityForSave(roomAmenity);
		RoomAmenityJpaEntity roomAmenityJpaEntity = mapper.toAmenityJpaEntity(roomAmenity);
		setupJpaRelations(roomAmenityJpaEntity);
		roomAmenityJpaEntity = roomAmenityRepository.save(roomAmenityJpaEntity);
		RoomAmenity createdAmenity = mapper.toRoomAmenity(roomAmenityJpaEntity);
		log.info("Created amenity : {}", createdAmenity);
		return createdAmenity;
	}

	@Override
	public List<Long> getAllAmenityIds() {
		return roomAmenityRepository.getAllAmenityIds();
	}

	@Override
	public void deleteAll(List<Long> tobeDeletedIds) {
		roomAmenityRepository.deleteAllById(tobeDeletedIds);
	}

	@Override
	public RoomAmenity getById(Long amenityId) {
		RoomAmenityJpaEntity roomAmenityJpaEntity = roomAmenityRepository.findById(amenityId).orElseThrow(() -> new RoomAmenityNotFoundException(amenityId));
		return mapper.toRoomAmenity(roomAmenityJpaEntity);
	}


	private void validateAmenityForSave(RoomAmenity roomAmenity) {
		if (roomAmenity == null) throw new AmenityPersistenceValidationException(ErrorMessages.AMENITY_WITHOUT_DETAILS);
	}

}
