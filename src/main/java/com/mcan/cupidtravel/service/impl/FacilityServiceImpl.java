package com.mcan.cupidtravel.service.impl;

import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.repository.FacilityRepository;
import com.mcan.cupidtravel.persistence.repository.FacilityTranslationRepository;
import com.mcan.cupidtravel.service.FacilityService;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.mapper.HotelMapper;
import com.mcan.cupidtravel.utils.constant.ErrorMessages;
import com.mcan.cupidtravel.utils.exception.FacilityNotFoundException;
import com.mcan.cupidtravel.utils.exception.FacilityPersistenceValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
	private static final Logger log = LoggerFactory.getLogger(FacilityServiceImpl.class);

	private final FacilityRepository facilityRepository;
	private final IdGenerator idGenerator;
	private final FacilityTranslationRepository facilityTranslationRepository;
	private final HotelMapper hotelMapper;


	@Override
	@Transactional
	public Facility create(Facility facility) {
		validateFacilityForSave(facility);
		FacilityJpaEntity facilityJpaEntity = hotelMapper.toFacilityJpaEntity(facility);
		setupJpaRelations(facilityJpaEntity);
		facilityJpaEntity = facilityRepository.save(facilityJpaEntity);
		Facility createdFacility = hotelMapper.toFacility(facilityJpaEntity);
		log.info("Created facility : {}", createdFacility);
		return createdFacility;
	}

	@Override
	@Transactional
	public void update(Long facilityId, Facility facility) {
		validateFacilityForSave(facility);
		FacilityJpaEntity existingFacility = facilityRepository.findById(facilityId).orElseThrow(() -> new FacilityNotFoundException(facilityId));
		existingFacility.setSort(facility.getSort());
		log.info("Updated facility: {}", facility);
		facilityTranslationRepository.deleteAll(existingFacility.getTranslation());
		existingFacility.getTranslation().forEach(translationJpa -> {
			translationJpa.setFacility(null);
			translationJpa.setFacilityId(null);
		});
		existingFacility.setTranslation(facility.getTranslation().stream().map(hotelMapper::toFacilityTranslationJpaEntity).collect(Collectors.toList()));
		setupJpaRelations(existingFacility);
		facilityRepository.save(existingFacility);
	}

	private void setupJpaRelations(FacilityJpaEntity facility) {
		if (facility == null) return;
		if (facility.getFacilityId() == null) {
			facility.setFacilityId(idGenerator.generateId());
		}
		facility.getTranslation().forEach(translation -> {
			if (translation.getFacilityTranslationId() == null) {
				translation.setFacilityTranslationId(idGenerator.generateId());
			}
			translation.setFacilityId(facility.getFacilityId());
		});
	}

	@Override
	public boolean existsById(long facilityId) {
		return facilityRepository.existsById(facilityId);
	}

	@Override
	public List<Facility> getAll() {
		return facilityRepository.findAll().stream().map(hotelMapper::toFacility).toList();
	}

	@Override
	public List<Long> getAllFacilityIds() {
		return facilityRepository.getAllFacilityIds();
	}

	@Override
	public void deleteAll(List<Long> tobeDeletedReviews) {
		facilityRepository.deleteAllById(tobeDeletedReviews);
	}

	@Override
	public Facility getById(long facilityId) {
		FacilityJpaEntity facility = facilityRepository.findById(facilityId).orElseThrow(() -> new FacilityNotFoundException(facilityId));
		return hotelMapper.toFacility(facility);
	}

	private void validateFacilityForSave(Facility facility) {
		if (facility == null) throw new FacilityPersistenceValidationException(ErrorMessages.FACILITY_WITHOUT_DETAILS);
		if (facility.getFacility() == null) throw new FacilityPersistenceValidationException(ErrorMessages.FACILITY_WITHOUT_NAME);
	}

}
