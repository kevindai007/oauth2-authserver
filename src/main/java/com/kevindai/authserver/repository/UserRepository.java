package com.kevindai.authserver.repository;

import com.kevindai.authserver.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByUsername(String username);

    List<UsersEntity> findByUsernameOrEmail(String username, String email);
}
