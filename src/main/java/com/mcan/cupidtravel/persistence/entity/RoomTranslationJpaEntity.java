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
@Table(name = "room_translation")
@Getter
@Setter
public class RoomTranslationJpaEntity implements Serializable {
	@Id
	@Column(nullable = false)
	private Long roomTranslationId;

	@Column(nullable = false)
	private String lang;

	@Column(nullable = false)
	private String roomName;

	@Column(nullable = false, length = 2048)
	private String description;

	@Column(name = "room_id", nullable = false)
	private Long roomId;

	@ManyToOne
	@JoinColumn(name = "room_id", insertable = false, updatable = false)
	private RoomJpaEntity room;

}