package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_contact_list")
@Getter
@Setter
public class UsersContactList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String emails;

    @Column(name = "shared_by")
    private String sharedBy;

    @Column(name = "action_date")
    private Instant actionDate;

    @Column(name = "invite_mail_date")
    private Instant inviteMailDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    private UserInformations userInfo;
}

