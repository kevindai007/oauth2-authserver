package com.kevindai.authserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "oauth2_clients_config")
public class Oauth2ClientConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth2_clients_config_id_gen")
    @SequenceGenerator(name = "oauth2_clients_config_id_gen", sequenceName = "oauth2_clients_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    @Column(name = "client_secret_hash", nullable = false)
    private String clientSecretHash;

    @Column(name = "client_name", length = 100)
    private String clientName;

    @Column(name = "redirect_uri", length = 256)
    private String redirectUri;

    @Column(name = "grant_types")
    private List<String> grantTypes;

    @Column(name = "scopes")
    private List<String> scopes;

    @ColumnDefault("'client_secret_basic'")
    @Column(name = "token_endpoint_auth_method", length = 50)
    private String tokenEndpointAuthMethod;

    @ColumnDefault("3600")
    @Column(name = "access_token_lifetime")
    private Integer accessTokenLifetime;

    @ColumnDefault("2592000")
    @Column(name = "refresh_token_lifetime")
    private Integer refreshTokenLifetime;

    @ColumnDefault("false")
    @Column(name = "require_consent")
    private Boolean requireConsent;

    @ColumnDefault("now()")
    @Column(name = "created_time")
    private Date createdTime;

    @ColumnDefault("now()")
    @Column(name = "updated_time")
    private Date updatedTime;

    @ColumnDefault("true")
    @Column(name = "enabled")
    private Boolean enabled;
}