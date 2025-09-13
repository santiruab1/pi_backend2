package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"roles"})
public class Permission extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "resource", nullable = false, length = 50)
    private String resource;
    
    @Column(name = "action", nullable = false, length = 50)
    private String action;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
}
