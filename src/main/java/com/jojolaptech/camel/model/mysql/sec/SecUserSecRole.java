package com.jojolaptech.camel.model.mysql.sec;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sec_user_sec_role")
@Getter
@Setter
public class SecUserSecRole {

    @EmbeddedId
    private SecUserSecRoleId id = new SecUserSecRoleId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("secUserId")
    @JoinColumn(name = "sec_user_id", nullable = false)
    private SecUser secUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("secRoleId")
    @JoinColumn(name = "sec_role_id", nullable = false)
    private SecRole secRole;
}

