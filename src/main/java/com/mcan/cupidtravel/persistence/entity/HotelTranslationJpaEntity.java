package com.mcan.cupidtravel.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "hotel_translation")
@Getter
@Setter
public class HotelTranslationJpaEntity implements Serializable {

	@Id
	@Column(nullable = false)
	private Long hotelTranslationId;

	@Column
	private String lang;

	@Column(length = 511)
	private String hotelName;

	@Column
	private String hotelType;

	@Column(length = 2048)
	private String description;

	@Column(length = 2048)
	private String markdownDescription;

	@Column(length = 2048)
	private String importantInfo;

	@Column(name = "hotel_id", nullable = false)
	private Long hotelId;

	@ManyToOne
	@JoinColumn(name = "hotel_id", insertable = false, updatable = false)
	private HotelJpaEntity hotel;
}
