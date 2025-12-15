package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.enums.ApiStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "activity_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityLogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestId;
    private Date requestDate;
    private Date responseDate;
    private String url;
    private String method;
    @Column(columnDefinition = "TEXT")
    private String requestHeader;
    @Column(columnDefinition = "TEXT")
    private String requestBody;
    private String requestParam;
    @Column(columnDefinition = "TEXT")
    private String response;
    private Integer statusCode;
    @Enumerated(EnumType.STRING)
    private ApiStatusEnum status;
    private String origin;
    private String device;
    private String ipAddress;
    private String userId;

    @PrePersist
    private void init() {
        requestDate = new Date();
    }

}