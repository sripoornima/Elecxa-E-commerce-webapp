package com.elecxa.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveDTO {

	private Long productId;
    private String subcategory;
    private String category;
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
    private String[] attributes;
}
