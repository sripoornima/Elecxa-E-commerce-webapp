package com.elecxa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_attributes")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attributeId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String attributeName;

    @Column(nullable = false)
    private String attributeValue;
}