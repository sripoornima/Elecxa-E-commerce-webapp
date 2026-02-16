package com.elecxa.dto;

import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String name;
    @JsonIgnore
    private List<SubCategoryDTO> subcategories;
}
