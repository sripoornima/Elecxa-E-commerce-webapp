package com.elecxa.model;

import jakarta.persistence.*;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderedDate;

    @Column(nullable = false)
    private LocalDateTime expectedDeliveryDate;

    @PrePersist
    protected void onCreate() {
        orderedDate = LocalDateTime.now();
    }
}
