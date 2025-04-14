package com.mcan.cupidtravel.rest.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcan.cupidtravel.rest.client.CupidClient;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.Review;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.utils.constant.Headers;
import com.mcan.cupidtravel.utils.property.CupidProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CupidClientImpl implements CupidClient {
    private static final Logger log = LoggerFactory.getLogger(CupidClientImpl.class);

    private final ObjectMapper objectMapper;
    private final CupidProperties cupidProperties;

    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public Hotel getHotelContent(Long hotelId) {
        try {
            return doHttpRequestToCupid(cupidProperties.getApiUrl() + hotelId, new TypeReference<Hotel>() {
            });
        } catch (IllegalStateException e) {
            log.error("Hotel with id :{} could not be fetched.", hotelId, e);
        }
        return null;
    }

    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public Hotel getHotelTranslation(Long hotelId, String lang) {
        try {
            return doHttpRequestToCupid(cupidProperties.getApiUrl() + hotelId + cupidProperties.getHotelTranslationApiUrlPath() + lang, new TypeReference<Hotel>() {
            });
        } catch (IllegalStateException e) {
            log.error("Hotel with id :{} could not be fetched.", hotelId, e);
        }
        return null;
    }

    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public List<Review> getReviewByHotelId(Long hotelId) {
        try {
            return doHttpRequestToCupid(cupidProperties.getApiUrl() + cupidProperties.getReviewApiUrlPath() + hotelId + "/" + cupidProperties.getReviewApiBatchSize(), new TypeReference<List<Review>>() {
            });
        } catch (IllegalStateException e) {
            log.error("Review with hotel id :{} could not be fetched.", hotelId, e);
        }
        return Collections.emptyList();
    }


    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public List<Facility> getAllFacilities() {
        try {
            return doHttpRequestToCupid(cupidProperties.getApiUrl() + cupidProperties.getFacilityApiUrlPath(), new TypeReference<List<Facility>>() {
            });
        } catch (IllegalStateException e) {
            log.error("Facilities could not be fetched.", e);
        }
        return Collections.emptyList();
    }

    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public List<RoomAmenity> getAllAmenities() {
        try {
            String urlString = cupidProperties.getApiUrl() + cupidProperties.getAmenityApiUrlPath();
            return doHttpRequestToCupid(urlString, new TypeReference<List<RoomAmenity>>() {
            });
        } catch (IllegalStateException e) {
            log.error("Amenities could not be fetched.", e);
        }
        return Collections.emptyList();
    }

    public <T> T doHttpRequestToCupid(String urlString, TypeReference<T> typeRef) throws IllegalStateException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header(Headers.ACCEPT_HEADER, Headers.ACCEPT_HEADER_APP_JSON_VALUE)
                    .header(cupidProperties.getApiTokenHeader(), cupidProperties.getApiToken())
                    .method(cupidProperties.getApiHttpMethod(), HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), typeRef);
        } catch (IOException | InterruptedException e) {
            log.error("Facilities could not be fetched.", e);
            Thread.currentThread().interrupt();
        }
        return null;
    }

}
