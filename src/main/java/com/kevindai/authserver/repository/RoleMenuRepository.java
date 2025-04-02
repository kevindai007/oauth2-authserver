package com.kevindai.authserver.repository;

import com.kevindai.authserver.entity.RoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, Integer> {
}
