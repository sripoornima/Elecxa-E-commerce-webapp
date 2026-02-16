package com.elecxa.model;


import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
