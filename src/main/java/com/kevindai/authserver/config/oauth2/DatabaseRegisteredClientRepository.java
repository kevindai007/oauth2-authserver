package com.kevindai.authserver.config.oauth2;

import com.kevindai.authserver.entity.Oauth2ClientConfigEntity;
import com.kevindai.authserver.repository.Oauth2ClientConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class DatabaseRegisteredClientRepository implements RegisteredClientRepository {

    private final Oauth2ClientConfigRepository clientConfigRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(RegisteredClient registeredClient) {
        Oauth2ClientConfigEntity entity = new Oauth2ClientConfigEntity();
        entity.setClientId(registeredClient.getClientId());
        entity.setClientSecretHash(registeredClient.getClientSecret()); // Already encoded
        entity.setClientName(registeredClient.getClientName());
        entity.setRedirectUri(
                registeredClient.getRedirectUris().stream().findFirst().orElse(null)
        );
        entity.setGrantTypes(
                registeredClient.getAuthorizationGrantTypes().stream()
                        .map(AuthorizationGrantType::getValue)
                        .toList()
        );
        entity.setScopes(
                new ArrayList<>(registeredClient.getScopes())
        );
        entity.setTokenEndpointAuthMethod(
                registeredClient.getClientAuthenticationMethods().stream()
                        .findFirst().map(ClientAuthenticationMethod::getValue).orElse("client_secret_basic")
        );
        entity.setAccessTokenLifetime(3600); // Or use a field from registeredClient if needed
        entity.setRefreshTokenLifetime(2592000);
        entity.setRequireConsent(
                registeredClient.getClientSettings().isRequireAuthorizationConsent()
        );
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        entity.setEnabled(true);

        clientConfigRepository.save(entity);
    }


    @Override
    public RegisteredClient findById(String id) {
        return clientConfigRepository.findById(Integer.valueOf(id))
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientConfigRepository.findAll().stream()
                .filter(entity -> entity.getClientId().equals(clientId))
                .findFirst()
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    private RegisteredClient toRegisteredClient(Oauth2ClientConfigEntity entity) {
        RegisteredClient.Builder builder = RegisteredClient
                .withId(entity.getId().toString())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecretHash()) // already hashed
                .clientAuthenticationMethod(new ClientAuthenticationMethod(entity.getTokenEndpointAuthMethod()))
                .clientName(entity.getClientName())
                .redirectUri(entity.getRedirectUri());

        entity.getGrantTypes().forEach(grantType ->
                builder.authorizationGrantType(new AuthorizationGrantType(grantType)));

        entity.getScopes().forEach(builder::scope);

        if (Boolean.FALSE.equals(entity.getRequireConsent())) {
            builder.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build());
        }

        return builder.build();
    }
}

