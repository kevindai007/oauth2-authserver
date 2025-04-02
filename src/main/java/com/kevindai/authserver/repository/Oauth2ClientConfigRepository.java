package com.kevindai.authserver.repository;

import com.kevindai.authserver.entity.Oauth2ClientConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2ClientConfigRepository extends JpaRepository<Oauth2ClientConfigEntity, Integer> {
}
