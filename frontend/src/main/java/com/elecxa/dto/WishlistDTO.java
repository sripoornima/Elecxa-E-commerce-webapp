package com.elecxa.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {
    private Long wishlistId;
    private Long userId;
    private Long productId;
    private String productAvailability;
}
