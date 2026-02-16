package com.elecxa.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long addressId;
    private Long userId;
    private String city;
    private String pincode;
    private String doorNoStreetName;
    private String landmark;
    private String district;
    private String state;
}
