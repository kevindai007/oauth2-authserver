package com.kevindai.authserver.controller;

import com.kevindai.authserver.service.jwt.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userinfo")
public class OIDCUserController {
    private final UserService userService;


    @GetMapping
    public Map<String, Object> getOIDCUserInfo() {
        return userService.getOIDCUser();
    }
}
