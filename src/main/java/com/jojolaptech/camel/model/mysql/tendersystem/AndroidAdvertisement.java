package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "android_advertisement")
@Getter
@Setter
public class AndroidAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_bottom")
    private String urlBottom;

    @Column(name = "banner_image_bottom")
    private String bannerImageBottom;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column
    private String description;

    @Column(name = "banner_image_top")
    private String bannerImageTop;

    @Column(name = "url_top")
    private String urlTop;
}

