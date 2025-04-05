package com.kevindai.authserver.config.oauth2;

import com.kevindai.authserver.dto.user.UserPrincipal;
import com.kevindai.authserver.service.jwt.users.UserClaimService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OIDCConfig {

    /**
     * this method will impact the how many info the client can obtain from OidcUser
     * @param userClaimService
     * @return
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(UserClaimService userClaimService) {
        return context -> {
            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                Authentication principal = context.getPrincipal();
                if (principal.getPrincipal() instanceof UserPrincipal user) {
                    Map<String, Object> claims = userClaimService.getUserClaims(user, context.getAuthorizedScopes());
                    claims.forEach(context.getClaims()::claim);
                }
            }
        };
    }


}
