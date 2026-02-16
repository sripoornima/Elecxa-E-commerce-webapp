package com.elecxa.service;

import com.elecxa.model.Cart;
import com.elecxa.model.CartItem;
import com.elecxa.model.Product;
import com.elecxa.model.User;
import com.elecxa.repository.CartRepository;
import com.elecxa.repository.ProductRepository;
import com.elecxa.repository.UserRepository;
import com.elecxa.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // Get the cart of a user
    public Cart getCart(Long userId) {
    	
    	User user = userRepository.findById(userId).get();
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    // Add an item to the cart
    public void addItemToCart(Long productId ,Long userId) {
    	Product product = productRepository.findById(productId).get();
    	User user = userRepository.findById(userId).get();

        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(1);
            cartItem.setPrice(product.getPrice());
            
            cartItem.setProduct(product);

            cart.getCartItems().add(cartItem);  // Add item to cart
            cartItemRepository.save(cartItem);  // Save the cart item
            cartRepository.save(cart);  // Save the updated cart
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    // Update the quantity of an item in the cart
    public void updateItemQuantity(Long itemId, String action) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(itemId);

        cartItemOptional.ifPresent(cartItem -> {
            if ("increase".equals(action)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else if ("decrease".equals(action) && cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() == 0  ? 0 : cartItem.getQuantity() - 1); 
            }
            cartItemRepository.save(cartItem);  // Save updated cart item
        });
    }

    // Remove an item from the cart
    public void removeItem(Long itemId) {
        cartItemRepository.deleteById(itemId);  // Remove item from database
    }

	public List<CartItem> getCartItems(long id) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByCart_CartId(id);
	}
}
