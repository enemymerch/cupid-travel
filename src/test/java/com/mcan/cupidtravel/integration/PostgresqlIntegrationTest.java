package com.mcan.cupidtravel.integration;

import com.mcan.cupidtravel.CupidTravelApplication;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CupidTravelApplication.class)
@AutoConfigureMockMvc
class PostgresqlIntegrationTest {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    void givenHotelsInDb_WhenGelAll_ThenReturnAllHotels() {
        insertHotels();
        assertEquals(2L, hotelRepository.count());
    }

    @Test
    @Transactional
    void givenHotelsInDb_WhenUpdate_ThenHotelIsUpdated() {
        insertHotels();
        HotelJpaEntity hotelJpaEntity = hotelRepository.findById(1L).get();
        assertNotNull(hotelJpaEntity);
        assertNotNull(hotelJpaEntity);
        hotelJpaEntity.setEmail("hotel@email.com");
        hotelRepository.save(hotelJpaEntity);
        hotelRepository.flush();

        HotelJpaEntity updatedEntity = hotelRepository.findById(hotelJpaEntity.getHotelId()).get();

        assertNotNull(updatedEntity);
        assertEquals(hotelJpaEntity.getHotelName(), updatedEntity.getHotelName());
        assertEquals(hotelJpaEntity.getHotelId(), updatedEntity.getHotelId());
    }

    @Test
    @Transactional
    void givenNoHotelsInDb_WhenSave_ThenHotelIsSaved() {
        HotelJpaEntity toBeCreatedHotel = buildHotel1();
        hotelRepository.save(toBeCreatedHotel);
        hotelRepository.flush();

        HotelJpaEntity createdHotel = hotelRepository.findById(1L).get();

        assertNotNull(createdHotel);
        assertEquals(createdHotel.getHotelId(), toBeCreatedHotel.getHotelId());
        assertEquals(createdHotel.getCupidId(), toBeCreatedHotel.getCupidId());
    }


    private void insertHotels() {
        HotelJpaEntity hotelJpaEntity1 = buildHotel1();
        HotelJpaEntity hotelJpaEntity2 = buildHotel2();

        hotelRepository.save(hotelJpaEntity1);
        hotelRepository.save(hotelJpaEntity2);
        hotelRepository.flush();
    }

    private HotelJpaEntity buildHotel1() {
        HotelJpaEntity hotelJpaEntity1 = new HotelJpaEntity();
        hotelJpaEntity1.setHotelId(1L);
        hotelJpaEntity1.setCupidId(1L);
        HotelTranslationJpaEntity translationJpa1 = new HotelTranslationJpaEntity();
        translationJpa1.setLang("en");
        translationJpa1.setHotelName("hotel1");
        translationJpa1.setHotelTranslationId(1L);
        translationJpa1.setHotelId(hotelJpaEntity1.getHotelId());
        translationJpa1.setHotel(hotelJpaEntity1);
        hotelJpaEntity1.setTranslation(Collections.singletonList(translationJpa1));
        return hotelJpaEntity1;
    }

    private HotelJpaEntity buildHotel2() {
        HotelJpaEntity hotel = new HotelJpaEntity();
        hotel.setHotelId(2L);
        hotel.setCupidId(2L);
        HotelTranslationJpaEntity translation = new HotelTranslationJpaEntity();
        translation.setLang("en");
        translation.setHotelName("hotel2");
        translation.setHotelTranslationId(2L);
        translation.setHotelId(hotel.getHotelId());
        translation.setHotel(hotel);
        hotel.setTranslation(Collections.singletonList(translation));
        return hotel;
    }

}