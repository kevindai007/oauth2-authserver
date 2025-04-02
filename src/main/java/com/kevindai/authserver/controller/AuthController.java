package com.kevindai.authserver.controller;

import com.kevindai.authserver.dto.user.UsersLoginRequestDto;
import com.kevindai.authserver.dto.user.UsersLoginResponseDto;
import com.kevindai.authserver.service.jwt.users.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsersService usersService;

    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody @Valid UsersLoginRequestDto usersLoginRequestDto) {
        UsersLoginResponseDto usersLoginResponseDto = usersService.login(usersLoginRequestDto);
        return ResponseEntity.ok(usersLoginResponseDto);
    }
}
