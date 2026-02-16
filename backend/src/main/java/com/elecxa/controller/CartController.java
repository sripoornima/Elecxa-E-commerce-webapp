package com.elecxa.controller;

import com.elecxa.model.Cart;
import com.elecxa.model.CartItem;
import com.elecxa.model.User;
import com.elecxa.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    
    
	@Autowired
    private CartService cartService;
	
	@GetMapping("/cartitem/{cartId}")
	public ResponseEntity<List<CartItem>> getCartItems(@PathVariable long cartId) {
	    List<CartItem> cartItems = cartService.getCartItems(cartId);  // Pass cartId to the service method
	    return ResponseEntity.ok(cartItems);
	}
	
	
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    // Add an item to the cart
    @PostMapping("add/{productId}/{userId}")
    public void addItemToCart(@PathVariable Long productId , @PathVariable Long userId) {
        cartService.addItemToCart(productId , userId);
    }

    // Update the quantity of an item
    @PutMapping("/update")
    public void updateItemQuantity(@RequestParam Long itemId, @RequestParam String action) {
        cartService.updateItemQuantity(itemId, action);
    }

    // Remove an item from the cart
    @DeleteMapping("/remove/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
    }
}
