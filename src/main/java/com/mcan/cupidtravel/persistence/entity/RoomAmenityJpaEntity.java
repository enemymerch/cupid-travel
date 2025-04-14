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
@Table(name = "room_amenity")
@Getter
@Setter
public class RoomAmenityJpaEntity implements Serializable {
	@Id
	@Column(name = "amenity_id")
	private Long amenityId;

	@Column
	private int sort;

	@ManyToMany(mappedBy = "amenities")
	private List<RoomJpaEntity> rooms;

	@OneToMany(mappedBy = "roomAmenity", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<RoomAmenityTranslationJpaEntity> translation;

	public String getAmenity() {
		Optional<RoomAmenityTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																					 .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																					 .findFirst();
		return currentLocaleTranslation.map(RoomAmenityTranslationJpaEntity::getAmenity).orElse(null);
	}
}