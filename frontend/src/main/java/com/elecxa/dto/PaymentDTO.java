package com.elecxa.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private Long userId;
    private Long orderId;
    private Long productId;
    private String paymentStatus;
    private BigDecimal amount;
    private String mode;
    private LocalDateTime date;
    private String paymentRefId;
}
