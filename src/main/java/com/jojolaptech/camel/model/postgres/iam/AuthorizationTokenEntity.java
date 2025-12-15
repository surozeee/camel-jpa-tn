package com.jojolaptech.camel.model.postgres.iam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "authorization_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizationTokenEntity {

    @Id
    @Column(length = 100)
    private String id;

    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Column(name = "principal_name", nullable = false, length = 150)
    private String principalName;

    @Column(name = "authorization_grant_type", nullable = false, length = 50)
    private String authorizationGrantType;

    @Column(name = "authorized_scopes", columnDefinition = "TEXT")
    private String authorizedScopes;

    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "access_token_value", columnDefinition = "TEXT")
    private String accessTokenValue;

    @Column(name = "access_token_issued_at", nullable = false)
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at", nullable = false)
    private Instant accessTokenExpiresAt;

    @Column(name = "access_token_metadata", columnDefinition = "TEXT")
    private String accessTokenMetadata;

    @Column(name = "refresh_token_value", columnDefinition = "TEXT")
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at", nullable = false)
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at", nullable = false)
    private Instant refreshTokenExpiresAt;

    @Column(name = "refresh_token_metadata", columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    @Column(name = "user_id", length = 100)
    private String userId;

}
