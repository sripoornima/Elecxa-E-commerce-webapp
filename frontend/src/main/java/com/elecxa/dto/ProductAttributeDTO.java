package com.elecxa.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDTO {
    private Long attributeId;
    private Long productId;
    private String attributeName;
    private String attributeValue;
}
