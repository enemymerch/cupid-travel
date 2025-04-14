package com.mcan.cupidtravel.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "review")
@Getter
@Setter
public class ReviewJpaEntity implements Serializable {
	@Id
	private Long reviewId;

	@Column(nullable = false, name = "hotel_id")
	private long hotelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id", insertable = false, updatable = false)
	private HotelJpaEntity hotel;

	@Column
	private short averageScore;

	@Column
	private String country;

	@Column
	private String type;

	@Column
	private String name;

	@Column
	private String date;

	@Column(length = 2048)
	private String headline;

	@Column
	private String language;

	@Column(length = 4096)
	private String pros;

	@Column(length = 4096)
	private String cons;

	@Column
	private String source;

}
