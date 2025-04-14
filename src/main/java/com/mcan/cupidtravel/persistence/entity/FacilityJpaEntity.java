package com.mcan.cupidtravel.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "facility")
@Getter
@Setter
public class FacilityJpaEntity implements Serializable {

	@Id
	@Column(name = "facility_id")
	private Long facilityId;

	@Column
	private int sort;

	@ManyToMany(mappedBy = "facilities")
	private List<HotelJpaEntity> hotels;

	@OneToMany(mappedBy = "facilityEntity", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<FacilityTranslationJpaEntity> translation;

	public String getFacility() {
		Optional<FacilityTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																					 .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																					 .findFirst();
		return currentLocaleTranslation.map(FacilityTranslationJpaEntity::getFacility).orElse(null);
	}

}