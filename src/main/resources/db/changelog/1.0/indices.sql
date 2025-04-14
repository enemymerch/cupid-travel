-- liquibase formatted sql

-- changeset mcan.yilmaz@gmail.com:cupid-travel-2 labels:initial,1.0,ddl
CREATE INDEX hotel_cupid_id_idx ON hotel (cupid_id);
CREATE INDEX hotel_rating_idx ON hotel (rating);
CREATE INDEX hotel_rating_hotel_id_idx ON hotel (rating,hotel_id);
CREATE INDEX hotel_hotel_id_cupid_id_idx ON hotel (hotel_id,cupid_id);


CREATE INDEX address_city_idx ON address (city);
CREATE INDEX address_city_country_idx ON address (city,country);
CREATE INDEX address_hotel_id_idx ON address (hotel_id,city,country);

CREATE INDEX hotel_translation_hotel_id_idx ON hotel_translation (hotel_id);
CREATE INDEX hotel_translation_hotel_id_lang_name_idx ON hotel_translation (hotel_id,lang,hotel_name);
CREATE INDEX hotel_translation_hotel_id_name_desc_type_lang_idx ON hotel_translation (hotel_id,hotel_name,description,lang,hotel_type);
CREATE INDEX hotel_translation_hotel_id_name_idx ON hotel_translation (hotel_id,hotel_name);
