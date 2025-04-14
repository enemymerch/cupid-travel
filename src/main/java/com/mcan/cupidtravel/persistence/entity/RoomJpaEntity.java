package com.mcan.cupidtravel.persistence.entity;


import com.mcan.cupidtravel.service.entity.BedType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "room")
@Getter
@Setter
public class RoomJpaEntity implements Serializable {
	@Id
	@Column(nullable = false, name = "room_id")
	private Long roomId;

	@Column(name = "hotel_id", nullable = false)
	private long hotelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id", insertable = false, updatable = false)
	private HotelJpaEntity hotel;

	@Column
	private int roomSizeSquare;

	@Column
	private String roomSizeUnit;

	@Column
	private int maxAdults;

	@Column
	private int maxChildren;

	@Column
	private int maxOccupancy;

	@OneToMany(mappedBy = "room", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	private List<RoomTranslationJpaEntity> translation;

	@ElementCollection
	@CollectionTable(name = "room_bed_types", joinColumns = @JoinColumn(name = "room_id"))
	private List<BedType> bedTypes;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		  name = "room_amenity_room_rel",
		  joinColumns = @JoinColumn(name = "room_id"),
		  inverseJoinColumns = @JoinColumn(name = "amenity_id")
	)
	private List<RoomAmenityJpaEntity> amenities;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PhotoJpaEntity> photos;


	public String getRoomName() {
		if (translation == null) {
			return null;
		}
		Optional<RoomTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				  .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				  .findFirst();
		return currentLocaleTranslation.map(RoomTranslationJpaEntity::getRoomName).orElse(null);
	}

	public String getDescription() {
		if (translation == null) {
			return null;
		}
		Optional<RoomTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				 .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				 .findFirst();
		return currentLocaleTranslation.map(RoomTranslationJpaEntity::getDescription).orElse(null);
	}

}
