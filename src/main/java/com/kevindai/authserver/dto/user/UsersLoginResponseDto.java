package com.kevindai.authserver.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UsersLoginResponseDto {
    private String token;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String scope;
    private String jti;

}
