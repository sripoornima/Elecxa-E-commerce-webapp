package com.elecxa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true, length = 100)
    private String city;

    @Column(nullable = true, length = 6)
    private String pincode;

    @Column(nullable = true, length = 255)
    private String doorNoStreetName;

    @Column(length = 255)
    private String landmark;

    @Column(nullable = true, length = 100)
    private String district;

    @Column(nullable = true, length = 100)
    private String state;
}

