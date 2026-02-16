package com.elecxa.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private SubCategoryDTO subcategory;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private BigDecimal discount;
    private String brand;
    private Double rating;
    private Integer warranty;
    private String imageUrl;

    @JsonIgnore
    private List<ProductAttributeDTO> attributes;
}
