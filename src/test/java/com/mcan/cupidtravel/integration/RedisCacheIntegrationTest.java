package com.mcan.cupidtravel.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.Serializable;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableCaching
@Testcontainers
public class RedisCacheIntegrationTest {

	@Container
	public static GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
		  .withExposedPorts(6379);

	@Autowired
	private MyService myService;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testCacheableMethod() {
		String id = "123";

		// İlk çağrı, cache'de olmadığı için gerçek metot çalışacak
		Hotel result1 = myService.getHotelById(id);
		assertThat(result1).isNotNull();

		// İkinci çağrı, cache'den okunacak
		Hotel result2 = myService.getHotelById(id);
		assertThat(result2).isEqualTo(result1);
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
										  .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
										  .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		}

		@Bean
		public RedisConnectionFactory redisConnectionFactory() {
			return new LettuceConnectionFactory(redisContainer.getHost(), redisContainer.getFirstMappedPort());
		}

		@Bean
		public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
			RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
																				.serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
																				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

			return RedisCacheManager.builder(redisConnectionFactory)
									.cacheDefaults(cacheConfiguration)
									.build();
		}

		@Bean
		public MyService myService() {
			return new MyService();
		}

	}

	static class MyService {
		@Cacheable(value = "hotelCache", key = "#id")
		public Hotel getHotelById(String id) {
			// Simulate a long-running method
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new Hotel(id, "Sample Hotel Name");
		}

	}

	static class Hotel implements Serializable {
		private static final long serialVersionUID = 1L;

		private String id;
		private String name;

		public Hotel() {}

		public Hotel(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Hotel hotel = (Hotel) o;
			return Objects.equals(id, hotel.id) && Objects.equals(name, hotel.name);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, name);
		}

	}

}