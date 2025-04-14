package com.mcan.cupidtravel.mapper.impl;

import com.mcan.cupidtravel.persistence.entity.AddressJpaEntity;
import com.mcan.cupidtravel.persistence.entity.FacilityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.FacilityTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelJpaEntity;
import com.mcan.cupidtravel.persistence.entity.HotelTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.entity.PhotoJpaEntity;
import com.mcan.cupidtravel.persistence.entity.PolicyJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomAmenityTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomJpaEntity;
import com.mcan.cupidtravel.persistence.entity.RoomTranslationJpaEntity;
import com.mcan.cupidtravel.persistence.repository.FacilityRepository;
import com.mcan.cupidtravel.service.entity.Address;
import com.mcan.cupidtravel.service.entity.Facility;
import com.mcan.cupidtravel.service.entity.FacilityTranslation;
import com.mcan.cupidtravel.service.entity.Hotel;
import com.mcan.cupidtravel.service.entity.HotelTranslation;
import com.mcan.cupidtravel.service.entity.Photo;
import com.mcan.cupidtravel.service.entity.Policy;
import com.mcan.cupidtravel.service.entity.Room;
import com.mcan.cupidtravel.service.entity.RoomAmenity;
import com.mcan.cupidtravel.service.entity.RoomAmenityTranslation;
import com.mcan.cupidtravel.service.entity.RoomTranslation;
import com.mcan.cupidtravel.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelMapperImpl implements HotelMapper {

	@Override
	public Hotel toHotel(HotelJpaEntity hotelJpaEntity) {
		if (hotelJpaEntity == null) return null;

		Hotel hotel = new Hotel();
		hotel.setHotelId(hotelJpaEntity.getHotelId());
		hotel.setCupidId(hotelJpaEntity.getCupidId());
		hotel.setMainImageTh(hotelJpaEntity.getMainImageTh());
		hotel.setHotelType(hotelJpaEntity.getHotelType());
		hotel.setMarkdownDescription(hotelJpaEntity.getMarkdownDescription());
		hotel.setImportantInfo(hotelJpaEntity.getImportantInfo());
		hotel.setDescription(hotelJpaEntity.getDescription());
		hotel.setHotelTypeId(hotelJpaEntity.getHotelTypeId());
		hotel.setChain(hotelJpaEntity.getChain());
		hotel.setChainId(hotelJpaEntity.getChainId());
		hotel.setLatitude(hotelJpaEntity.getLatitude());
		hotel.setLongitude(hotelJpaEntity.getLongitude());
		hotel.setHotelName(hotelJpaEntity.getHotelName());
		hotel.setPhone(hotelJpaEntity.getPhone());
		hotel.setFax(hotelJpaEntity.getFax());
		hotel.setEmail(hotelJpaEntity.getEmail());
		hotel.setAddress(toAddress(hotelJpaEntity.getAddress()));
		hotel.setStars(hotelJpaEntity.getStars());
		hotel.setAirportCode(hotelJpaEntity.getAirportCode());
		hotel.setRating(hotelJpaEntity.getRating());
		hotel.setReviewCount(hotelJpaEntity.getReviewCount());
		hotel.setCheckinStart(hotelJpaEntity.getCheckinStart());
		hotel.setCheckinEnd(hotelJpaEntity.getCheckinEnd());
		hotel.setCheckout(hotelJpaEntity.getCheckout());
		hotel.setParking(hotelJpaEntity.getParking());
		hotel.setGroupRoomMin(hotelJpaEntity.getGroupRoomMin());

		return hotel;
	}


	private Address toAddress(AddressJpaEntity address) {
		if (address == null) return null;
		return Address.builder()
					  .address(address.getAddress())
					  .city(address.getCity())
					  .country(address.getCountry())
					  .build();
	}


	@Override
	public HotelJpaEntity toHotelJpaEntity(Hotel hotel) {
		if (hotel == null) return null;

		HotelJpaEntity hotelJpaEntity = new HotelJpaEntity();
		hotelJpaEntity.setHotelId(hotel.getHotelId());
		hotelJpaEntity.setCupidId(hotel.getCupidId());
		hotelJpaEntity.setMainImageTh(hotel.getMainImageTh());
		hotelJpaEntity.setHotelTypeId(hotel.getHotelTypeId());
		hotelJpaEntity.setChain(hotel.getChain());
		hotelJpaEntity.setChainId(hotel.getChainId());
		hotelJpaEntity.setLatitude(hotel.getLatitude());
		hotelJpaEntity.setLongitude(hotel.getLongitude());
		hotelJpaEntity.setPhone(hotel.getPhone());
		hotelJpaEntity.setFax(hotel.getFax());
		hotelJpaEntity.setEmail(hotel.getEmail());
		hotelJpaEntity.setStars(hotel.getStars());
		hotelJpaEntity.setAirportCode(hotel.getAirportCode());
		hotelJpaEntity.setRating(hotel.getRating());
		hotelJpaEntity.setReviewCount(hotel.getReviewCount());
		hotelJpaEntity.setCheckinStart(hotel.getCheckinStart());
		hotelJpaEntity.setCheckinEnd(hotel.getCheckinEnd());
		hotelJpaEntity.setCheckout(hotel.getCheckout());
		hotelJpaEntity.setParking(hotel.getParking());
		hotelJpaEntity.setGroupRoomMin(hotel.getGroupRoomMin());
		if (hotel.getAddress() != null) {
			hotelJpaEntity.setAddress(toAddressJpaEntity(hotel.getAddress()));
		}
		if (hotel.getPhotos() != null) {
			hotelJpaEntity.setPhotos(hotel.getPhotos().stream().map(this::toPhotoJpaEntity).toList());
		}
		if (hotel.getPolicies() != null) {
			hotelJpaEntity.setPolicies(hotel.getPolicies().stream().map(this::toPolicyJpaEntity).toList());
		}
		if (hotel.getRooms() != null) {
			hotelJpaEntity.setRooms(hotel.getRooms().stream().map(this::toRoomJpaEntity).toList());
		}
		if (hotel.getTranslation() != null) {
			hotelJpaEntity.setTranslation(hotel.getTranslation().stream().map(this::toHotelTranslationJpaEntity).toList());
		}
		if (hotel.getFacilities() != null) {
			hotelJpaEntity.setFacilities(hotel.getFacilities().stream().map(this::toFacilityJpaEntity).toList());
		}
		return hotelJpaEntity;
	}

	@Override
	public FacilityJpaEntity toFacilityJpaEntity(Facility facility) {
		if (facility == null) {
			return null;
		}
		FacilityJpaEntity facilityJpaEntity = new FacilityJpaEntity();
		facilityJpaEntity.setFacilityId(facility.getFacilityId());
		if (facility.getTranslation() != null) {
			facilityJpaEntity.setTranslation(facility.getTranslation().stream().map(this::toFacilityTranslationJpaEntity).toList());
		}
		return facilityJpaEntity;
	}

	@Override
	public FacilityTranslationJpaEntity toFacilityTranslationJpaEntity(FacilityTranslation facilityTranslation) {
		if (facilityTranslation == null) return null;
		FacilityTranslationJpaEntity facilityTranslationJpaEntity = new FacilityTranslationJpaEntity();
		facilityTranslationJpaEntity.setFacility(facilityTranslation.getFacility());
		facilityTranslationJpaEntity.setLang(facilityTranslation.getLang());
		return facilityTranslationJpaEntity;
	}

	private FacilityTranslation toFacilityTranslation(FacilityTranslationJpaEntity translationJpa) {
		if (translationJpa == null) return null;
		FacilityTranslation facilityTranslation = new FacilityTranslation();
		facilityTranslation.setFacility(translationJpa.getFacility());
		facilityTranslation.setLang(translationJpa.getLang());
		return facilityTranslation;
	}

	@Override
	public Facility toFacility(FacilityJpaEntity facilityJpaEntity) {
		if (facilityJpaEntity == null) {
			return null;
		}
		Facility facility = new Facility();
		facility.setFacilityId(facilityJpaEntity.getFacilityId());
		facility.setFacility(facilityJpaEntity.getFacility());
		facility.setSort(facilityJpaEntity.getSort());
		if (facilityJpaEntity.getTranslation() != null) {
			facility.setTranslation(facilityJpaEntity.getTranslation().stream().map(this::toFacilityTranslation).toList());
		}
		return facility;
	}

	private HotelTranslationJpaEntity toHotelTranslationJpaEntity(HotelTranslation hotelTranslation) {
		if (hotelTranslation == null) {
			return null;
		}
		HotelTranslationJpaEntity hotelTranslationJpaEntity = new HotelTranslationJpaEntity();
		hotelTranslationJpaEntity.setLang(hotelTranslation.getLang());
		hotelTranslationJpaEntity.setHotelName(hotelTranslation.getHotelName());
		hotelTranslationJpaEntity.setHotelType(hotelTranslation.getHotelType());
		hotelTranslationJpaEntity.setDescription(hotelTranslation.getDescription());
		hotelTranslationJpaEntity.setMarkdownDescription(hotelTranslation.getMarkdownDescription());
		hotelTranslationJpaEntity.setImportantInfo(hotelTranslation.getImportantInfo());
		return hotelTranslationJpaEntity;
	}

	private RoomJpaEntity toRoomJpaEntity(Room room) {
		if (room == null) {
			return null;
		}
		RoomJpaEntity roomJpaEntity = new RoomJpaEntity();
		roomJpaEntity.setRoomId(room.getId());
		roomJpaEntity.setRoomSizeSquare(room.getRoomSizeSquare());
		roomJpaEntity.setRoomSizeUnit(room.getRoomSizeUnit());
		roomJpaEntity.setMaxAdults(room.getMaxAdults());
		roomJpaEntity.setMaxChildren(room.getMaxChildren());
		roomJpaEntity.setMaxOccupancy(room.getMaxOccupancy());
		roomJpaEntity.setBedTypes(room.getBedTypes());
		if (room.getRoomAmenities() != null) roomJpaEntity.setAmenities(room.getRoomAmenities().stream().map(this::toRoomAmenityJpaEntity).toList());
		if (room.getPhotos() != null) roomJpaEntity.setPhotos(room.getPhotos().stream().map(this::toPhotoJpaEntity).toList());
		if (room.getTranslations() != null) roomJpaEntity.setTranslation(room.getTranslations().stream().map(this::toRoomTranslationJpaEntity).toList());
		return roomJpaEntity;
	}

	private RoomAmenityJpaEntity toRoomAmenityJpaEntity(RoomAmenity roomAmenity) {
		if (roomAmenity == null) return null;
		RoomAmenityJpaEntity roomAmenityJpaEntity = new RoomAmenityJpaEntity();
		roomAmenityJpaEntity.setAmenityId(roomAmenity.getAmenityId());
		return roomAmenityJpaEntity;
	}

	private RoomTranslationJpaEntity toRoomTranslationJpaEntity(RoomTranslation roomTranslation) {
		if (roomTranslation == null) {
			return null;
		}
		RoomTranslationJpaEntity roomTranslationJpaEntity = new RoomTranslationJpaEntity();
		roomTranslationJpaEntity.setRoomName(roomTranslation.getRoomName());
		roomTranslationJpaEntity.setLang(roomTranslation.getLang());
		roomTranslationJpaEntity.setDescription(roomTranslation.getDescription());
		return roomTranslationJpaEntity;
	}

	private PolicyJpaEntity toPolicyJpaEntity(Policy policy) {
		if (policy == null) {
			return null;
		}
		PolicyJpaEntity policyJpaEntity = new PolicyJpaEntity();
		policyJpaEntity.setParking(policy.getParking());
		policyJpaEntity.setName(policy.getName());
		policyJpaEntity.setDescription(policy.getDescription());
		policyJpaEntity.setPolicyType(policy.getPolicyType());
		policyJpaEntity.setDescription(policy.getDescription());
		policyJpaEntity.setChildAllowed(policy.getChildAllowed());
		policyJpaEntity.setPetsAllowed(policy.getPetsAllowed());
		policyJpaEntity.setParking(policy.getParking());
		return policyJpaEntity;
	}

	private PhotoJpaEntity toPhotoJpaEntity(Photo photo) {
		if (photo == null) {
			return null;
		}
		PhotoJpaEntity photoJpaEntity = new PhotoJpaEntity();
		photoJpaEntity.setUrl(photo.getUrl());
		photoJpaEntity.setHdUrl(photo.getHdUrl());
		photoJpaEntity.setImageDescription(photo.getImageDescription());
		photoJpaEntity.setImageClass1(photo.getImageClass1());
		photoJpaEntity.setImageClass2(photo.getImageClass2());
		photoJpaEntity.setMainPhoto(photo.isMainPhoto());
		photoJpaEntity.setScore(photo.getScore());
		photoJpaEntity.setClassId(photo.getClassId());
		photoJpaEntity.setClassOrder(photo.getClassOrder());
		return photoJpaEntity;
	}

	private AddressJpaEntity toAddressJpaEntity(Address address) {
		if (address == null) return null;

		AddressJpaEntity addressJpaEntity = new AddressJpaEntity();
		addressJpaEntity.setAddress(address.getAddress());
		addressJpaEntity.setCity(address.getCity());
		addressJpaEntity.setCountry(address.getCountry());

		return addressJpaEntity;
	}

	@Override
	public Hotel toDetailedHotel(HotelJpaEntity hotelJpaEntity) {
		if (hotelJpaEntity == null) return null;
		Hotel hotel = this.toHotel(hotelJpaEntity);
		hotel.setRooms(toRooms(hotelJpaEntity.getRooms()));
		hotel.setTranslation(toHotelTranslations(hotelJpaEntity.getTranslation()));
		hotel.setPhotos(toPhotos(hotelJpaEntity.getPhotos()));
		hotel.setFacilities(toFacilities(hotelJpaEntity.getFacilities()));
		hotel.setPolicies(toPolicies(hotelJpaEntity.getPolicies()));
		return hotel;
	}

	private List<Policy> toPolicies(List<PolicyJpaEntity> policies) {
		if (policies == null) return Collections.emptyList();
		return policies.stream().map(policyJpaEntity -> Policy.builder().policyType(policyJpaEntity.getPolicyType())
															  .childAllowed(policyJpaEntity.getChildAllowed())
															  .description(policyJpaEntity.getDescription())
															  .name(policyJpaEntity.getName())
															  .build()).toList();
	}

	private List<Facility> toFacilities(List<FacilityJpaEntity> hotelJpaEntity) {
		if (hotelJpaEntity == null) return Collections.emptyList();
		return hotelJpaEntity.stream().map(this::toFacility).toList();
	}

	private List<Photo> toPhotos(List<PhotoJpaEntity> hotelJpaEntity) {
		if (hotelJpaEntity == null) return Collections.emptyList();
		return hotelJpaEntity.stream().map(photoJpaEntity -> Photo.builder()
																  .url(photoJpaEntity.getUrl()).hdUrl(photoJpaEntity.getHdUrl())
																  .score(photoJpaEntity.getScore()).mainPhoto(photoJpaEntity.isMainPhoto())
																  .classId(photoJpaEntity.getClassId()).classOrder(photoJpaEntity.getClassOrder())
																  .imageClass2(photoJpaEntity.getImageClass2()).imageClass1(photoJpaEntity.getImageClass1()).imageDescription(photoJpaEntity.getImageDescription())
																  .build()).toList();
	}

	private List<HotelTranslation> toHotelTranslations(List<HotelTranslationJpaEntity> hotelJpaEntity) {
		if (hotelJpaEntity == null) return Collections.emptyList();

		return hotelJpaEntity.stream().map(hotelTranslationJpaEntity -> {
			HotelTranslation hotelTranslation = new HotelTranslation();
			hotelTranslation.setLang(hotelTranslationJpaEntity.getLang());
			hotelTranslation.setHotelName(hotelTranslationJpaEntity.getHotelName());
			hotelTranslation.setHotelType(hotelTranslationJpaEntity.getHotelType());
			hotelTranslation.setDescription(hotelTranslationJpaEntity.getDescription());
			hotelTranslation.setMarkdownDescription(hotelTranslationJpaEntity.getMarkdownDescription());
			hotelTranslation.setImportantInfo(hotelTranslationJpaEntity.getImportantInfo());
			return hotelTranslation;
		}).toList();
	}

	private List<Room> toRooms(List<RoomJpaEntity> hotelJpaEntity) {
		if (hotelJpaEntity == null) return Collections.emptyList();
		return hotelJpaEntity.stream().map(this::toRoom).toList();
	}

	private Room toRoom(RoomJpaEntity roomJpaEntity) {
		if (roomJpaEntity == null) return null;
		return Room.builder().id(roomJpaEntity.getRoomId()).roomSizeSquare(roomJpaEntity.getRoomSizeSquare()).roomSizeUnit(roomJpaEntity.getRoomSizeUnit())
				   .maxAdults(roomJpaEntity.getMaxAdults()).maxChildren(roomJpaEntity.getMaxChildren()).maxOccupancy(roomJpaEntity.getMaxOccupancy())
				   .roomName(roomJpaEntity.getRoomName()).description(roomJpaEntity.getDescription()).roomAmenities(toRoomAmenities(roomJpaEntity.getAmenities()))
				   .photos(toPhotos(roomJpaEntity.getPhotos())).translations(toRoomTranslations(roomJpaEntity.getTranslation())).bedTypes(roomJpaEntity.getBedTypes()).build();
	}

	public List<RoomTranslation> toRoomTranslations(List<RoomTranslationJpaEntity> translation) {
		if (translation == null) return Collections.emptyList();
		return translation.stream().map(roomTranslationJpaEntity -> {
			RoomTranslation roomTranslation = new RoomTranslation();
			roomTranslation.setLang(roomTranslationJpaEntity.getLang());
			roomTranslation.setRoomName(roomTranslationJpaEntity.getRoomName());
			roomTranslation.setDescription(roomTranslationJpaEntity.getDescription());
			return roomTranslation;
		}).toList();
	}

	private List<RoomAmenity> toRoomAmenities(List<RoomAmenityJpaEntity> amenities) {
		if (amenities == null) return Collections.emptyList();
		return amenities.stream().map(this::toRoomAmenity).toList();
	}


	@Override
	public RoomAmenityTranslationJpaEntity toRoomAmenityTranslationJpaEntity(RoomAmenityTranslation translation) {
		if (translation == null) return null;
		RoomAmenityTranslationJpaEntity jpaEntity = new RoomAmenityTranslationJpaEntity();
		jpaEntity.setAmenity(translation.getAmenity());
		jpaEntity.setLang(translation.getLang());
		return jpaEntity;
	}

	@Override
	public RoomAmenity toRoomAmenity(RoomAmenityJpaEntity jpaEntity) {
		if (jpaEntity == null) {
			return null;
		}
		RoomAmenity roomAmenity = new RoomAmenity();
		roomAmenity.setAmenityId(jpaEntity.getAmenityId());
		roomAmenity.setAmenity(jpaEntity.getAmenity());
		roomAmenity.setSort(jpaEntity.getSort());
		if (jpaEntity.getTranslation() != null) {
			roomAmenity.setTranslation(jpaEntity.getTranslation().stream().map(this::toRoomAmenityTranslation).toList());
		}
		return roomAmenity;
	}


	private RoomAmenityTranslation toRoomAmenityTranslation(RoomAmenityTranslationJpaEntity roomAmenityTranslationJpaEntity) {
		if (roomAmenityTranslationJpaEntity == null) {
			return null;
		}
		RoomAmenityTranslation roomAmenityTranslation = new RoomAmenityTranslation();
		roomAmenityTranslation.setAmenity(roomAmenityTranslationJpaEntity.getAmenity());
		roomAmenityTranslation.setLang(roomAmenityTranslationJpaEntity.getLang());
		return roomAmenityTranslation;
	}

	@Override
	public RoomAmenityJpaEntity toAmenityJpaEntity(RoomAmenity roomAmenity) {
		if (roomAmenity == null) {
			return null;
		}
		RoomAmenityJpaEntity roomAmenityJpaEntity = new RoomAmenityJpaEntity();
		roomAmenityJpaEntity.setAmenityId(roomAmenity.getAmenityId());
		roomAmenityJpaEntity.setSort(roomAmenity.getSort());
		roomAmenityJpaEntity.setTranslation(roomAmenity.getTranslation().stream().map(this::toRoomAmenityTranslationJpaEntity).toList());
		return roomAmenityJpaEntity;
	}

}
