package com.kevindai.authserver.service.jwt.users;

import com.kevindai.authserver.dto.user.UserPrincipal;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class UserClaimService {

    public Map<String, Object> getUserClaims(UserPrincipal user, Set<String> scopes) {
        Map<String, Object> claims = new HashMap<>();

        // Always include sub (subject)
        claims.put(IdTokenClaimNames.SUB, user.getUsername());
        claims.put("name", user.getFullName());

        if (scopes.contains("email") && user.getEmail() != null) {
            claims.put("email", user.getEmail());
        }

        if (scopes.contains("profile")) {
            claims.put("name", user.getFullName());
            claims.put("preferred_username", user.getUsername());
        }

//        claims.put("roles", user.getRoles());
        return claims;
    }
}

