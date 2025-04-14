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
@Table(name = "room_amenity_translation")
@Getter
@Setter
public class RoomAmenityTranslationJpaEntity implements Serializable {
	@Id
	@Column(nullable = false)
	private Long amenityTranslationId;

	@Column(nullable = false)
	private String lang;

	@Column(nullable = false, length = 512)
	private String amenity;

	@Column(name = "amenity_id", nullable = false)
	private Long amenityId;

	@ManyToOne
	@JoinColumn(name = "amenity_id", insertable = false, updatable = false)
	private RoomAmenityJpaEntity roomAmenity;
}
