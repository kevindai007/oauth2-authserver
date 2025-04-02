package com.kevindai.authserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "menu")
public class MenuEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    @Size(max = 50)
    @Column(name = "icon", length = 50)
    private String icon;

    @NotNull
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @ColumnDefault("true")
    @Column(name = "is_visible")
    private Boolean isVisible;

    @Size(max = 100)
    @Column(name = "required_permission", length = 100)
    private String requiredPermission;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}