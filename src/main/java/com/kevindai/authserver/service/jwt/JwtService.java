package com.kevindai.authserver.service.jwt;

import com.kevindai.authserver.dto.jwt.JwtTokenInfo;
import com.kevindai.authserver.dto.user.UserInfoDto;
import com.kevindai.authserver.dto.user.UsersLoginRequestDto;
import com.kevindai.authserver.entity.RefreshTokenEntity;
import com.kevindai.authserver.entity.UsersEntity;
import com.kevindai.authserver.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class JwtService {
    public static String SECRET_KEY = null;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtDecoder jwtDecoder;

    public JwtService(RefreshTokenRepository refreshTokenRepository, JwtDecoder jwtDecoder) {
        try {
            KeyGenerator hmacSHA256 = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = hmacSHA256.generateKey();
            SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            this.jwtDecoder = jwtDecoder;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public JwtTokenInfo generateAccessToken(UsersLoginRequestDto usersLoginRequestDto, List<String> permissions) {
        return generateToken(usersLoginRequestDto.getUsername(), permissions);
    }

    public JwtTokenInfo generateAccessToken(UserInfoDto user, List<String> permissions) {
        return generateToken(user.getUsername(), permissions);
    }

    private JwtTokenInfo generateToken(String username, List<String> permissions) {
        String jti = UUID.randomUUID().toString();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 10); // 10 hours

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("permissions", permissions);

        String token = Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .id(jti)
                .signWith(getKey())
                .compact();

        return JwtTokenInfo.builder()
                .token(token)
                .expiresIn(expiryDate.getTime() - now.getTime())
                .jti(jti)
                .build();
    }

    private SecretKey getKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public RefreshTokenEntity generateRefreshToken(UsersEntity user, List<String> permissions) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserId(Math.toIntExact(user.getId()));
        refreshToken.setClientId(null);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(30).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());  // Set refresh token to expire in 30 days
        refreshToken.setScope(String.join(" ", permissions));

        return refreshTokenRepository.save(refreshToken);
    }


    public String extractUsername(String token) {
        return decodeJwt(token).getSubject(); // "sub": "kevin"
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwt jwt = decodeJwt(token);
            String username = jwt.getSubject();
            return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(Jwt jwt) {
        Instant exp = jwt.getExpiresAt();
        return exp != null && exp.isBefore(Instant.now());
    }

    private Jwt decodeJwt(String token) {
        return jwtDecoder.decode(token);
    }
}
