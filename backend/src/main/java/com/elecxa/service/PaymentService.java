package com.elecxa.service;

import com.elecxa.model.*;
import com.elecxa.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

	@Value("${razorpay.key_id}")
    private String razorpayKey;

    @Value("${razorpay.key_secret}")
    private String razorpaySecret;
    
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> getPaymentByRefId(String refId) {
        return paymentRepository.findByPaymentRefId(refId);
    }

    public List<Payment> getPaymentsByUser(User user) {
        return paymentRepository.findByUser(user);
    }

    public List<Payment> getPaymentsByProduct(Product product) {
        return paymentRepository.findByProduct(product);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public Map<String, Object> initiatePayment(double amount) {
        Map<String, Object> responseData = new HashMap<>();

        try {
            RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int)(amount * 100)); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", UUID.randomUUID().toString());
            orderRequest.put("payment_capture", 1);

        
            responseData.put("amount", amount);
            responseData.put("currency", "INR");
            responseData.put("razorpayKey", razorpayKey);
        } catch (RazorpayException e) {
            throw new RuntimeException("Payment initiation failed", e);
        }

        return responseData;
    }
}
