package com.jojolaptech.camel.model.mysql.sec;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class SecUserSecRoleId implements Serializable {

    @Column(name = "sec_user_id")
    private Long secUserId;

    @Column(name = "sec_role_id")
    private Long secRoleId;
}

