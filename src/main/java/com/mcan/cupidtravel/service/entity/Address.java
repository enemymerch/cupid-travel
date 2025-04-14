package com.mcan.cupidtravel.service.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Address implements Serializable {
	private String address;
	private String city;
	private String country;

}
