package com.elecxa.repository;

import com.elecxa.model.Cart;
import com.elecxa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
