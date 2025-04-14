package com.mcan.cupidtravel.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name = "photo")
@Getter
@Setter
public class PhotoJpaEntity implements Serializable {

    @Id
    private Long photoId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "hotel_id", insertable = false, updatable = false)
    private HotelJpaEntity hotel;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private RoomJpaEntity room;

    @Column
    private String url;

    @Column
    private String hdUrl;

    @Column
    private String imageDescription;

    @Column
    private String imageClass1;

    @Column
    private String imageClass2;

    @Column
    private boolean mainPhoto;

    @Column
    private double score;

    @Column
    private long classId;

    @Column
    private int classOrder;

}