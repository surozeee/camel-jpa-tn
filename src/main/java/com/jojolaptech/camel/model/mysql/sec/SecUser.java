package com.jojolaptech.camel.model.mysql.sec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sec_user")
@Getter
@Setter
public class SecUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean accountExpired = false;

    @Column(nullable = false)
    private boolean accountLocked = false;

    @Column(nullable = false)
    private boolean passwordExpired = false;

    @Column
    private Boolean isVerified;

    @OneToMany(mappedBy = "secUser", fetch = FetchType.EAGER)
    private Set<SecUserSecRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "secUser", fetch = FetchType.EAGER)
    private Set<SecondaryEmail> secondaryEmails = new HashSet<>();

    @OneToMany(mappedBy = "secUser")
    private Set<VerificationCode> verificationCodes = new HashSet<>();
}

