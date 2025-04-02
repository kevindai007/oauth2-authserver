package com.kevindai.authserver.dto.jwt;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtTokenInfo {
    private String token;
    private long expiresIn;
    private String jti;
}
