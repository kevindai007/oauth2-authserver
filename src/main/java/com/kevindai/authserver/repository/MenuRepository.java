package com.kevindai.authserver.repository;

import com.kevindai.authserver.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    @Query("""
            select m.requiredPermission from UsersEntity u inner join UserRoleEntity ur on u.id = ur.userId
            inner join RoleEntity r on ur.roleId = r.id
            inner join RoleMenuEntity rm on r.id = rm.roleId
            inner join MenuEntity m on rm.menuId = m.id
            where u.id = :userId
            """)
    List<String> getPermissionsByUserId(Long userId);
}
