package com.elecxa.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subcategory;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(length = 1000)
    private String description;

    private BigDecimal discount;

    private String brand;

    private Double rating;

    private Integer warranty; 
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductAttribute> attributes;
}
