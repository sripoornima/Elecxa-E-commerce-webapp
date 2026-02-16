package com.elecxa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    
    private RoleType roleName;
}

