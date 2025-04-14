-- liquibase formatted sql

-- changeset mcan.yilmaz@gmail.com:cupid-travel-1 labels:initial,1.0,ddl
CREATE TABLE amenity (
                         sort int4 NULL,
                         amenity_id int8 NOT NULL,
                         CONSTRAINT amenity_pkey PRIMARY KEY (amenity_id)
);

CREATE TABLE facility (
                          sort int4 NULL,
                          facility_id int8 NOT NULL,
                          CONSTRAINT facility_pkey PRIMARY KEY (facility_id)
);
CREATE TABLE facility_translation_jpa_entity (
                                                 facility_id int8 NOT NULL,
                                                 facility_translation_id int8 NOT NULL,
                                                 facility varchar(255) NOT NULL,
                                                 lang varchar(255) NOT NULL,
                                                 CONSTRAINT facility_translation_jpa_entity_pkey PRIMARY KEY (facility_translation_id)
);

CREATE TABLE hotel (
                       child_allowed bool NULL,
                       group_room_min int4 NULL,
                       latitude float8 NULL,
                       longitude float8 NULL,
                       pets_allowed bool NULL,
                       rating float8 NULL,
                       review_count int4 NULL,
                       stars int4 NULL,
                       chain_id int8 NULL,
                       cupid_id int8 NOT NULL,
                       hotel_id int8 NOT NULL,
                       hotel_type_id int8 NULL,
                       airport_code varchar(255) NULL,
                       "chain" varchar(255) NULL,
                       checkin_end varchar(255) NULL,
                       checkin_start varchar(255) NULL,
                       checkout varchar(255) NULL,
                       email varchar(255) NULL,
                       fax varchar(255) NULL,
                       main_image_th varchar(255) NULL,
                       parking varchar(255) NULL,
                       phone varchar(255) NULL,
                       CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id)
);

CREATE TABLE room_amenity (
                              sort int4 NULL,
                              amenity_id int8 NOT NULL,
                              CONSTRAINT room_amenity_pkey PRIMARY KEY (amenity_id)
);

CREATE TABLE address (
                         hotel_id int8 NOT NULL,
                         address varchar(255) NULL,
                         city varchar(255) NULL,
                         country varchar(255) NULL,
                         CONSTRAINT address_pkey PRIMARY KEY (hotel_id),
                         CONSTRAINT fksesqdwm9ca8dud5t44wn8xgl4 FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

CREATE TABLE facility_translation (
                                      facility_id int8 NOT NULL,
                                      facility_translation_id int8 NOT NULL,
                                      facility varchar(255) NOT NULL,
                                      lang varchar(255) NOT NULL,
                                      CONSTRAINT facility_translation_pkey PRIMARY KEY (facility_translation_id),
                                      CONSTRAINT fkq18dlpowhbkr06h85ur8a70u FOREIGN KEY (facility_id) REFERENCES facility(facility_id)
);

CREATE TABLE hotel_facility (
                                facility_id int8 NOT NULL,
                                hotel_id int8 NOT NULL,
                                CONSTRAINT fk2kh8p1kmqv9m5d62cxsxwrauu FOREIGN KEY (facility_id) REFERENCES facility(facility_id),
                                CONSTRAINT fk9sc9e92apu176v84w6xbhbvae FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

CREATE TABLE hotel_translation (
                                   hotel_id int8 NOT NULL,
                                   hotel_translation_id int8 NOT NULL,
                                   hotel_name varchar(511) NULL,
                                   description varchar(2048) NULL,
                                   important_info varchar(2048) NULL,
                                   markdown_description varchar(2048) NULL,
                                   hotel_type varchar(255) NULL,
                                   lang varchar(255) NULL,
                                   CONSTRAINT hotel_translation_pkey PRIMARY KEY (hotel_translation_id),
                                   CONSTRAINT fknbwjd3uqob16uses813own7me FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

CREATE TABLE policy (
                        hotel_id int8 NOT NULL,
                        policy_id int8 NOT NULL,
                        description varchar(2048) NULL,
                        child_allowed varchar(255) NULL,
                        "name" varchar(255) NULL,
                        parking varchar(255) NULL,
                        pets_allowed varchar(255) NULL,
                        policy_type varchar(255) NULL,
                        CONSTRAINT policy_pkey PRIMARY KEY (policy_id),
                        CONSTRAINT fkpyu85xipypsb447iib6ie3r4b FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

CREATE TABLE review (
                        average_score int2 NULL,
                        hotel_id int8 NOT NULL,
                        review_id int8 NOT NULL,
                        headline varchar(2048) NULL,
                        cons varchar(4096) NULL,
                        pros varchar(4096) NULL,
                        country varchar(255) NULL,
                        "date" varchar(255) NULL,
                        "language" varchar(255) NULL,
                        "name" varchar(255) NULL,
                        "source" varchar(255) NULL,
                        "type" varchar(255) NULL,
                        CONSTRAINT review_pkey PRIMARY KEY (review_id),
                        CONSTRAINT fki0ly7ivbh8ijdgoi7cwtuoavt FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

CREATE TABLE room (
                      max_adults int4 NULL,
                      max_children int4 NULL,
                      max_occupancy int4 NULL,
                      room_size_square int4 NULL,
                      hotel_id int8 NOT NULL,
                      room_id int8 NOT NULL,
                      room_size_unit varchar(255) NULL,
                      CONSTRAINT room_pkey PRIMARY KEY (room_id),
                      CONSTRAINT fkdosq3ww4h9m2osim6o0lugng8 FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)
);

CREATE TABLE room_amenity_room_rel (
                                       amenity_id int8 NOT NULL,
                                       room_id int8 NOT NULL,
                                       CONSTRAINT fkmisw2b8sy69w75745p8am53mx FOREIGN KEY (room_id) REFERENCES room(room_id),
                                       CONSTRAINT fkrty6qgq4caemrgw5um78d6w4e FOREIGN KEY (amenity_id) REFERENCES room_amenity(amenity_id)
);


CREATE TABLE room_amenity_translation (
                                          amenity_id int8 NOT NULL,
                                          amenity_translation_id int8 NOT NULL,
                                          amenity varchar(512) NOT NULL,
                                          lang varchar(255) NOT NULL,
                                          CONSTRAINT room_amenity_translation_pkey PRIMARY KEY (amenity_translation_id),
                                          CONSTRAINT fk2j7qpwi0vcai74x01bg1tq076 FOREIGN KEY (amenity_id) REFERENCES room_amenity(amenity_id)
);

CREATE TABLE room_bed_types (
                                quantity int4 NULL,
                                room_id int8 NOT NULL,
                                bed_size varchar(255) NULL,
                                bed_type varchar(255) NULL,
                                CONSTRAINT fkmdb3tybxvufkvw1or1iv4sqnh FOREIGN KEY (room_id) REFERENCES room(room_id)
);

CREATE TABLE room_translation (
                                  room_id int8 NOT NULL,
                                  room_translation_id int8 NOT NULL,
                                  description varchar(2048) NOT NULL,
                                  lang varchar(255) NOT NULL,
                                  room_name varchar(255) NOT NULL,
                                  CONSTRAINT room_translation_pkey PRIMARY KEY (room_translation_id),
                                  CONSTRAINT fk57jfndlcturltnjul9giko4qu FOREIGN KEY (room_id) REFERENCES room(room_id)
);

CREATE TABLE photo (
                       class_order int4 NULL,
                       main_photo bool NULL,
                       score float8 NULL,
                       class_id int8 NULL,
                       hotel_id int8 NULL,
                       photo_id int8 NOT NULL,
                       room_id int8 NULL,
                       hd_url varchar(255) NULL,
                       image_class1 varchar(255) NULL,
                       image_class2 varchar(255) NULL,
                       image_description varchar(255) NULL,
                       url varchar(255) NULL,
                       CONSTRAINT photo_pkey PRIMARY KEY (photo_id),
                       CONSTRAINT fk4sf4oxc24usshr60sx6hf0167 FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id),
                       CONSTRAINT fk97j0brycov37q9i6bhc1as9k2 FOREIGN KEY (room_id) REFERENCES room(room_id)
);