package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "super_admin_settings")
@Getter
@Setter
public class SuperAdminSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Lob
    private String design;
}

