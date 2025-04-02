package com.kevindai.authserver.service.jwt.users;

import com.kevindai.authserver.dto.user.UserInfoDto;
import com.kevindai.authserver.dto.user.UserPrincipal;
import com.kevindai.authserver.entity.UsersEntity;
import com.kevindai.authserver.exception.AuthErrorCode;
import com.kevindai.authserver.repository.UserRepository;
import com.kevindai.base.projcore.exception.BizException;
import io.jsonwebtoken.lang.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * this class is used to implement the UserDetailsService interface
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }

    public List<UsersEntity> findAll() {
        return userRepository.findAll();
    }


    public UserInfoDto getUserInfo(String username) {
        UsersEntity usersEntity = userRepository.findByUsername(username);
        if (usersEntity == null) {
            return null;
        }
        return UserInfoDto.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .email(usersEntity.getEmail())
                .createdTime(usersEntity.getCreatedTime())
                .updateTime(usersEntity.getUpdateTime())
                .build();
    }

    public UserInfoDto createUserInfo(String username, String password, String email) {
        if (Objects.isEmpty(username)) {
            throw new BizException(AuthErrorCode.INVALID_USERNAME.getHttpStatusCode(), AuthErrorCode.INVALID_USERNAME.getErrorCode(), AuthErrorCode.INVALID_USERNAME.getMessage());
        }
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(username);
        usersEntity.setPassword(password);
        usersEntity.setEmail(email);
        usersEntity.setCreatedTime(new Date());
        usersEntity.setUpdateTime(new Date());
        userRepository.save(usersEntity);
        return UserInfoDto.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .email(usersEntity.getEmail())
                .createdTime(usersEntity.getCreatedTime())
                .updateTime(usersEntity.getUpdateTime())
                .build();
    }

    public UserInfoDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserInfoDto userInfo = getUserInfo(userDetails.getUsername());
            return userInfo;
        }
        return null;
    }

}
