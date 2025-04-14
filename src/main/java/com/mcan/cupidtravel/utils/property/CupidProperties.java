package com.mcan.cupidtravel.utils.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "cupid")
public class CupidProperties {
	private String apiUrl;
	private String apiTokenHeader;
	private String apiToken;
	private String apiHttpMethod;
	private String reviewApiUrlPath;
	private int reviewApiBatchSize;
	private String facilityApiUrlPath;
	private String amenityApiUrlPath;
	private String hotelTranslationApiUrlPath;
	private Long[] hotelIds;
}
