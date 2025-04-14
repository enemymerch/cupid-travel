package com.mcan.cupidtravel.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "policy")
@Getter
@Setter
public class PolicyJpaEntity implements Serializable {

	@Id
	private Long policyId;

	@Column(name = "hotel_id", nullable = false)
	private Long hotelId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id", insertable = false, updatable = false)
	private HotelJpaEntity hotel;

	@Column
	private String policyType;

	@Column
	private String name;

	@Column(length = 2048)
	private String description;

	@Column
	private String childAllowed;

	@Column
	private String petsAllowed;

	@Column
	private String parking;
}
