package com.elecxa.repository;

import com.elecxa.model.Payment;
import com.elecxa.model.User;
import com.elecxa.model.Order;
import com.elecxa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUser(User user);

    List<Payment> findByOrder(Order order);

    List<Payment> findByProduct(Product product);

    Optional<Payment> findByPaymentRefId(String paymentRefId); 
}
