package com.elecxa.model;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductAvailabilityStatus productAvailability;
}

