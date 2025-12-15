package com.jojolaptech.camel.model.postgres.iam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientEntity {
	@Id
	@Column(length = 100)
	private String id;

	@Column(name = "client_id", nullable = false, unique = true, length = 100)
	private String clientId;

	@Column(name = "client_id_issued_at", nullable = false)
	private Instant clientIdIssuedAt;

	@Column(name = "client_secret", length = 100, nullable = false)
	private String clientSecret;

	@Column(name = "client_secret_expires_at")
	private Instant clientSecretExpiresAt;

	@Column(name = "client_name", length = 50, nullable = false)
	private String clientName;

	@Column(name = "client_authentication_methods", length = 1000, nullable = false)
	private String clientAuthenticationMethods;

	@Column(name = "authorization_grant_types", length = 1000, nullable = false)
	private String authorizationGrantTypes;

	@Column(name = "redirect_uris", length = 1000)
	private String redirectUris;

	@Column(name = "post_logout_redirect_uris", length = 1000)
	private String postLogoutRedirectUris;

	@Column(name = "scopes", length = 1000)
	private String scopes;

	@Column(name = "client_settings", length = 2000, nullable = false)
	private String clientSettings;

	@Column(name = "token_settings", length = 2000, nullable = false)
	private String tokenSettings;
}
