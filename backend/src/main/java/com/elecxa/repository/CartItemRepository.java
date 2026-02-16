package com.elecxa.repository;

import com.elecxa.model.CartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByCart_CartId(long id);
    
}
