package com.elecxa.dto;


import lombok.Data;

@Data
public class CheckoutFormDTO {
	
    private String firstName;
    private String LastName;

    private String email;

    private String phoneNumber;

    private String doorNoStreetName;

    private String pincode;

    private String city;

    private String state;
}
