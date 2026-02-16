package com.elecxa.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private UserDTO user;
    private ProductDTO product;
    private BigDecimal totalAmount;
    private String orderStatus;
    private LocalDateTime orderedDate;
    private LocalDateTime expectedDeliveryDate;
    private String customerName;
    private String paymentMethod;
    private String productName;

}
