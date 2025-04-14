package com.mcan.cupidtravel.service.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Review implements Serializable {

	private short averageScore;
	private String country;
	private String type;
	private String name;
	private String date;
	private String headline;
	private String language;
	private String pros;
	private String cons;
	private String source;
	private Long hotelId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Review review = (Review) o;
		return averageScore == review.averageScore &&
			  Objects.equals(country, review.country) &&
			  Objects.equals(type, review.type) &&
			  Objects.equals(name, review.name) &&
			  Objects.equals(date, review.date) &&
			  Objects.equals(headline, review.headline) &&
			  Objects.equals(language, review.language) &&
			  Objects.equals(pros, review.pros) &&
			  Objects.equals(cons, review.cons) &&
			  Objects.equals(source, review.source);
	}

	@Override
	public int hashCode() {
		return Objects.hash(averageScore, country, type, name, date, headline, language, pros, cons, source);
	}

}
