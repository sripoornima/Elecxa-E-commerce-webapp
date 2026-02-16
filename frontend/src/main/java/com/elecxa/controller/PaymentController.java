package com.elecxa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/checkout/payment")
public class PaymentController {

	@Autowired
    public RestTemplate restTemplate;


    @PostMapping("/initiate")
    public void initiatePayment(@RequestParam BigDecimal amount, Model model, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	String token = (String)session.getAttribute("accessToken");
        headers.setBasicAuth(token);
        
        HttpEntity<String> entity = new HttpEntity<>("amount=" + amount, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/api/payments/create",
                HttpMethod.POST, entity, String.class
        );

        model.addAttribute("razorpayOrder", response.getBody());
        model.addAttribute("amount", amount);
    }

    @PostMapping("/confirm")
    public String confirmPayment(@RequestParam Map<String, String> data, HttpSession session) {
        String paymentId = data.get("razorpay_payment_id");
        String orderId = data.get("razorpay_order_id");
        String signature = data.get("razorpay_signature");
        String amount = data.get("amount");

        String url = "http://localhost:8080/api/payments/save" +
                "?paymentId=" + paymentId +
                "&razorpayOrderId=" + orderId +
                "&signature=" + signature +
                "&amount=" + amount;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	String token = (String)session.getAttribute("accessToken");
        headers.setBasicAuth(token);
        HttpEntity<String> entity = new HttpEntity<>( headers);

        restTemplate.exchange(url, HttpMethod.POST, entity , String.class);
        return "redirect:/customer/orders";
    }
}
