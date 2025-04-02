package com.kevindai.authserver.service.jwt.users;

import com.kevindai.authserver.dto.user.UsersLoginRequestDto;
import com.kevindai.authserver.dto.jwt.JwtTokenInfo;
import com.kevindai.authserver.dto.user.UsersLoginResponseDto;
import com.kevindai.authserver.entity.RefreshTokenEntity;
import com.kevindai.authserver.entity.UsersEntity;
import com.kevindai.authserver.repository.UserRepository;
import com.kevindai.authserver.service.jwt.JwtService;
import com.kevindai.base.projcore.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public UsersLoginResponseDto login(UsersLoginRequestDto usersLoginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersLoginRequestDto.getUsername(), usersLoginRequestDto.getPassword()));
        if (!authenticate.isAuthenticated()) {
            throw new BizException(HttpStatus.UNAUTHORIZED);
        }
        UsersEntity usersEntity = userRepository.findByUsername(usersLoginRequestDto.getUsername());
        List<String> permissions = permissionService.getPermissionsByUserId(usersEntity.getId());


        JwtTokenInfo tokenInfo = jwtService.generateAccessToken(usersLoginRequestDto, permissions);
        RefreshTokenEntity refreshTokenEntity = jwtService.generateRefreshToken(usersEntity, permissions);
        return UsersLoginResponseDto.builder()
                .token(tokenInfo.getToken())
                .refreshToken(refreshTokenEntity.getToken())
                .tokenType("Bearer")
                .expiresIn(tokenInfo.getExpiresIn())
                .jti(tokenInfo.getJti())
                .build();
    }

}
