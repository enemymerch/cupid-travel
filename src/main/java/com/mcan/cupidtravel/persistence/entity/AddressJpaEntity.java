package com.mcan.cupidtravel.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "address")
@Getter
@Setter
public class AddressJpaEntity implements Serializable {

	@Id
	@Column(name = "hotel_id", nullable = false)
	private long hotelId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "hotel_id")
	private HotelJpaEntity hotel;

	@Column
	private String address;

	@Column
	private String city;

	@Column
	private String country;


}
