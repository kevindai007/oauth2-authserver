package com.kevindai.authserver.controller;

import com.kevindai.authserver.dto.user.UserInfoDto;
import com.kevindai.authserver.service.jwt.users.UserService;
import com.kevindai.authserver.service.jwt.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final UsersService usersService;


    /**
     * get current user info
     *
     * @return
     */
    @GetMapping("/user")
    public UserInfoDto getUserInfo() {
        return userService.getCurrentUser();
    }

//
//    @PostMapping("/login")
//    public UsersLoginResponseDto login(@RequestBody @Valid UsersLoginRequestDto usersLoginRequestDto) {
//        return usersService.login(usersLoginRequestDto);
//    }
}
