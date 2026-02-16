package com.elecxa.controller;

import java.math.BigDecimal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.elecxa.dto.CartDTO;
import com.elecxa.dto.CartItemDTO;
import com.elecxa.dto.ProductDTO;
import com.elecxa.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Display the cart page
    @GetMapping
    public String viewCart(Model model , @RequestParam long id , HttpSession session) {
    	String token = (String)session.getAttribute("accessToken");
    	CartDTO cart = cartService.getCart(id,token);
        List<CartItemDTO> cartItems = cartService.getCartItems(cart.getCartId() , token);
        
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (CartItemDTO item : cartItems) {
            subtotal = subtotal.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))); // total price for each item
            totalDiscount = totalDiscount.add(item.getProduct().getDiscount());
        }

        BigDecimal shipping = BigDecimal.ZERO;
        BigDecimal total = subtotal.subtract(totalDiscount).add(shipping);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("discount", totalDiscount);
        model.addAttribute("total", total);
        
        
        return "user/addcart-new";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("itemId") Long itemId,
                                 @RequestParam("action") String action,
                                 Model model , HttpSession session) {
    	long id = (long)session.getAttribute("userId");
    	String token = (String)session.getAttribute("accessToken");

        cartService.updateItemQuantity(itemId, action,  token);
        return "redirect:/cart?id="+id;  // Redirect back to the cart page
    }
    
    @GetMapping("/addtocart/{productId}")
    public String updateCart(@PathVariable Long productId, Model model  , HttpSession session) {
    	String token = (String)session.getAttribute("accessToken");

    	long id = (long)session.getAttribute("userId");
        cartService.updateCart(productId,id , token);
        return "redirect:/product/{productId}";  // Redirect back to the cart page
    }

    // Remove an item from the cart
    @GetMapping("/remove")
    public String removeItem(@RequestParam("itemId") Long itemId , HttpSession session) {
    	String token = (String)session.getAttribute("accessToken");

    	long id = (long)session.getAttribute("userId");
        cartService.removeItem(itemId , token);
        return "redirect:/cart?id="+id;  // Redirect back to the cart page
    }
}
