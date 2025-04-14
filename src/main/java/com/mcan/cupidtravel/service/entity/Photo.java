package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode
@Builder
public class Photo implements Serializable {
	private String url;
	private String hdUrl;
	private String imageDescription;
	private String imageClass1;
	private String imageClass2;
	private boolean mainPhoto;
	private double score;
	private long classId;
	private int classOrder;

}
