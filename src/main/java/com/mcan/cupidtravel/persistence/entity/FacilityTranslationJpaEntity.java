package com.mcan.cupidtravel.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "facility_translation")
@Getter
@Setter
public class FacilityTranslationJpaEntity implements Serializable {

    @Id
    @Column(nullable = false)
    private Long facilityTranslationId;

    @Column(nullable = false)
    private String lang;

    @Column(nullable = false)
    private String facility;

    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @ManyToOne
    @JoinColumn(name = "facility_id", insertable = false, updatable = false)
    private FacilityJpaEntity facilityEntity;

}
