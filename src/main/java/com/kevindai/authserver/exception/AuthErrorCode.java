package com.kevindai.authserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    INVALID_GRANT_TYPE(1001, 400, "Invalid grant type"),
    INVALID_CLIENT(1002, 400, "Invalid client"),
    INVALID_CLIENT_SECRET(1003, 400, "Invalid client secret"),
    INVALID_USERNAME(1004, 400, "Invalid username"),
    INVALID_PASSWORD(1005, 400, "Invalid password"),
    INVALID_REFRESH_TOKEN(1006, 400, "Invalid refresh token"),
    INVALID_ACCESS_TOKEN(1007, 400, "Invalid access token"),
    INVALID_TOKEN(1008, 400, "Invalid token"),
    INVALID_SCOPE(1009, 400, "Invalid scope"),
    INVALID_TOKEN_TYPE(1010, 400, "Invalid token type"),
    INVALID_TOKEN_FORMAT(1011, 400, "Invalid token format"),
    INVALID_ID_USER_INFO(1012, 400, "Invalid user info"),


    ;
    private final int errorCode;
    private final int httpStatusCode;
    private final String message;
    }
