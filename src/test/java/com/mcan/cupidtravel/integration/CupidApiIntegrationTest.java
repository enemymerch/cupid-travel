package com.mcan.cupidtravel.integration;

import com.mcan.cupidtravel.CupidTravelApplication;
import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import com.mcan.cupidtravel.persistence.repository.FacilityRepository;
import com.mcan.cupidtravel.persistence.repository.HotelRepository;
import com.mcan.cupidtravel.persistence.repository.RoomAmenityRepository;
import com.mcan.cupidtravel.service.AmenityOnboardingService;
import com.mcan.cupidtravel.service.FacilityOnboardingService;
import com.mcan.cupidtravel.service.HotelOnboardingService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(
	  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	  classes = CupidTravelApplication.class)
@AutoConfigureMockMvc
@Import({CupidApiIntegrationTest.TestConfig.class, TestcontainersConfiguration.class})
@TestPropertySource(properties = "scheduler.enabled=false")
class CupidApiIntegrationTest {


	@Container
	public static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
		  .withExposedPorts(6379);

	@Autowired
	private FacilityOnboardingService facilityOnboardingService;

	@Autowired
	private AmenityOnboardingService amenityOnboardingService;

	@Autowired
	private HotelOnboardingService hotelOnboardingService;

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private RoomAmenityRepository roomAmenityRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Test
	@Transactional
	@Order(1)
	void whenFacilitiesOnboardedThenSavedToDb() {
		facilityOnboardingService.onboardFacilities();
		List<FacilityJpaEntity> facilityJpaEntities = facilityRepository.findAll();
		assertNotNull(facilityJpaEntities);
		assertNotEquals(facilityJpaEntities.size(), 0);
	}

	@Test
	@Transactional
	@Order(2)
	void whenAmenitiesOnboardedThenSavedToDb() {
		amenityOnboardingService.onboardAmenities();
		List<RoomAmenityJpaEntity> jpaEntities = roomAmenityRepository.findAll();
		assertNotNull(jpaEntities);
		assertNotEquals(jpaEntities.size(), 0);
	}

	@Test
	@Transactional
	@Order(3)
	void whenHotelsOnboardedThenSavedToDb() {
		facilityOnboardingService.onboardFacilities();
		amenityOnboardingService.onboardAmenities();


		hotelOnboardingService.onboardHotels();
		List<HotelJpaEntity> jpaEntities = hotelRepository.findAll();
		assertNotNull(jpaEntities);
		assertNotEquals(jpaEntities.size(), 0);
	}

	@Configuration
	static class TestConfig {

		@Bean
		public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
			RedisTemplate<String, Object> template = new RedisTemplate<>();
			template.setConnectionFactory(connectionFactory);
			template.setKeySerializer(new StringRedisSerializer());
			template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
			return template;
		}

		@Bean
		public RedisCacheConfiguration cacheConfiguration() {
			return RedisCacheConfiguration.defaultCacheConfig()
										  .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
										  .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		}

		@Bean
		public RedisConnectionFactory redisConnectionFactory() {
			System.setProperty("spring.data.redis.host", redisContainer.getHost());
			System.setProperty("spring.data.redis.port", redisContainer.getMappedPort(6379).toString());
			return new LettuceConnectionFactory(redisContainer.getHost(), redisContainer.getMappedPort(6379));
		}

		@Bean
		public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
			RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
																				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
																				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

			return RedisCacheManager.builder(redisConnectionFactory)
									.cacheDefaults(cacheConfiguration)
									.build();
		}

	}

}