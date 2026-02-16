package com.elecxa.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = true, length = 50)
    private String lastName;

    @Column(nullable = true, unique = false, length = 100)
    private String email;

    @Column(nullable = true, unique = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = true)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime accountCreationDate;

    @PrePersist
    protected void onCreate() {
        accountCreationDate = LocalDateTime.now();
    }
}
