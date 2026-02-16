package com.elecxa.service;

import com.elecxa.model.OtpVerification;
import com.elecxa.repository.OtpVerificationRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

	@Autowired
    private JavaMailSender mailSender;
	
    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.phone_number}")
    private String fromPhoneNumber;

    private final OtpVerificationRepository otpRepository;

    public OtpService(OtpVerificationRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateAndSendOtp(String phoneNumber) {
        // Generate a random 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Save OTP to database
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setPhoneNumber(phoneNumber);
        otpVerification.setOtp(otp);
        otpVerification.setGeneratedTime(LocalDateTime.now());
        otpRepository.save(otpVerification);
System.out.println(phoneNumber);
        if(phoneNumber.contains("@")) {
        	SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(phoneNumber);
            message.setSubject("OTP verification");
            
            String emailBody = "Sent from Java Mail Sender - your OTP code is " + otp ;

            message.setText(emailBody);
            mailSender.send(message);
            return "OTP sent successfully to " + phoneNumber;
        }
        Twilio.init(accountSid, authToken);

        Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                "Your OTP code is: " + otp
        ).create();

        return "OTP sent successfully to " + phoneNumber;
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
    	Optional<OtpVerification> otpRecord;

    	if(phoneNumber.contains("@")) {
            otpRecord = otpRepository.findByEmail(phoneNumber.trim().substring(2));
    	}
    	else {
    		otpRecord = otpRepository.findByPhoneNumber("+"+(phoneNumber.trim()));
    	}
    	
    	System.out.println(otpRecord.get().getOtp()+"jnj");
        if (otpRecord.isPresent()) {
            OtpVerification storedOtp = otpRecord.get();

            if (storedOtp.getOtp().equals(otp) &&
                storedOtp.getGeneratedTime().isAfter(LocalDateTime.now().minusMinutes(5))) {
                return true;
            }
        }
        return false;
    }
}
