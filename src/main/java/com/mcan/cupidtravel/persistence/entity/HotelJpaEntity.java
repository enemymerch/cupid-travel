package com.mcan.cupidtravel.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "hotel")
@Getter
@Setter
public class HotelJpaEntity implements Serializable {

	@Id
	@Column(nullable = false)
	private Long hotelId;

	@Column(nullable = false)
	private Long cupidId;

	@Column
	private String mainImageTh;

	@Column
	private long hotelTypeId;

	@Column
	private String chain;

	@Column
	private long chainId;

	@Column
	private double latitude;

	@Column
	private double longitude;

	@Column
	private String phone;

	@Column
	private String fax;

	@Column
	private String email;

	@Column
	private int stars;

	@Column
	private String airportCode;

	@Column
	private double rating;

	@Column
	private int reviewCount;

	@Column
	private String checkinStart;

	@Column
	private String checkinEnd;

	@Column
	private String checkout;

	@Column
	private String parking;

	@Column
	private int groupRoomMin;

	@Column
	private boolean childAllowed;

	@Column
	private boolean petsAllowed;

	@OneToMany(mappedBy = "hotel", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<HotelTranslationJpaEntity> translation;

	@OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
	private AddressJpaEntity address;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PhotoJpaEntity> photos;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PolicyJpaEntity> policies;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RoomJpaEntity> rooms;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReviewJpaEntity> reviews;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		  name = "hotel_facility",
		  joinColumns = @JoinColumn(name = "hotel_id"),
		  inverseJoinColumns = @JoinColumn(name = "facility_id")
	)
	private List<FacilityJpaEntity> facilities;

	public String getHotelName() {
		if (translation == null) {
			return null;
		}
		Optional<HotelTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				  .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				  .findFirst();
		return currentLocaleTranslation.map(HotelTranslationJpaEntity::getHotelName).orElse(null);
	}

	public String getHotelType() {
		if (translation == null) {
			return null;
		}
		Optional<HotelTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				  .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				  .findFirst();
		return currentLocaleTranslation.map(HotelTranslationJpaEntity::getHotelType).orElse(null);
	}

	public String getDescription() {
		if (translation == null) {
			return null;
		}
		Optional<HotelTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				  .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				  .findFirst();
		return currentLocaleTranslation.map(HotelTranslationJpaEntity::getDescription).orElse(null);
	}

	public String getMarkdownDescription() {
		if (translation == null) {
			return null;
		}
		Optional<HotelTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				  .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				  .findFirst();
		return currentLocaleTranslation.map(HotelTranslationJpaEntity::getMarkdownDescription).orElse(null);
	}


	public String getImportantInfo() {
		if (translation == null) {
			return null;
		}
		Optional<HotelTranslationJpaEntity> currentLocaleTranslation = translation.stream()
																				  .filter(translationEntity -> translationEntity.getLang().equals(LocaleContextHolder.getLocale().getLanguage()))
																				  .findFirst();
		return currentLocaleTranslation.map(HotelTranslationJpaEntity::getImportantInfo).orElse(null);
	}

}
