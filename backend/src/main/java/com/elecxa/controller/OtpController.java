package com.elecxa.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.elecxa.exception.InvalidOtpException;
import com.elecxa.service.OtpService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/customer/otp/send")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
		if(phoneNumber.contains("@")) {
			String response = otpService.generateAndSendOtp(phoneNumber.substring(3));
	        return ResponseEntity.ok(response);
		}
        String response = otpService.generateAndSendOtp("+" + (phoneNumber.trim()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customer/otp/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(phoneNumber.trim(), otp);
        if (!isValid) {
            throw new InvalidOtpException("Invalid OTP or OTP Expired!");
        }

        return ResponseEntity.ok("OTP Verified Successfully!");
    }
}
