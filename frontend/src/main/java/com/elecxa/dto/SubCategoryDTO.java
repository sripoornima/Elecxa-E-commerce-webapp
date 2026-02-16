package com.elecxa.dto;

import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryDTO {
    private Long subcategoryId;
    private String name;
    private CategoryDTO category;
    @JsonIgnore
    private List<ProductDTO> products;
}
