package com.elecxa.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.elecxa.dto.AddressDTO;
import com.elecxa.dto.CartDTO;
import com.elecxa.dto.CartItemDTO;
import com.elecxa.dto.CheckoutFormDTO;
import com.elecxa.dto.UserDTO;
import com.elecxa.service.AddressService;
import com.elecxa.service.CartService;
import com.elecxa.service.ProductService;
import com.elecxa.service.ProfileService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private AddressService addressService;

	@Autowired
    private CartService cartService;
	
	@Autowired
    private RestTemplate restTemplate;

    @Autowired 
    ProductService productService;
    
    private String backendUrl;

    @GetMapping("/")
    public String showCheckoutForm(Model model  , HttpSession session) {
    	long id = (long)session.getAttribute("userId");
    	String token = (String)session.getAttribute("accessToken");

    	UserDTO userProfile = profileService.getUserProfile(id , token);
    	AddressDTO userAddress = addressService.getUserAddress(id , token);
    	CheckoutFormDTO checkoutform = new CheckoutFormDTO();
    	
    	checkoutform.setDoorNoStreetName(userAddress.getDoorNoStreetName() == null ? " " : userAddress.getDoorNoStreetName());
    	checkoutform.setCity(userAddress.getCity());
    	checkoutform.setEmail(userProfile.getEmail());
    	checkoutform.setFirstName(userProfile.getFirstName());
    	checkoutform.setLastName(userProfile.getLastName());
    	checkoutform.setPhoneNumber(userProfile.getPhoneNumber());
    	checkoutform.setPincode(userAddress.getPincode());
    	checkoutform.setState(userAddress.getState());
       
    	model.addAttribute("checkoutForm", checkoutform);

        CartDTO cart = cartService.getCart(id, token); // calling backend for cart
        List<CartItemDTO> cartItems = cartService.getCartItems(cart.getCartId(), token);
        System.out.println(cartItems);
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

        return "user/checkout";
    }

    @PostMapping("/submit")
    public String submitCheckoutForm(
             @ModelAttribute("checkoutForm") CheckoutFormDTO checkoutForm,
            BindingResult result,
            Model model
    ) {
//        if (result.hasErrors()) {
//            CartDTO cart = cartService.getCart();
//            model.addAttribute("cart", cart);
//            return "user/addcart-new";
//        }

//        boolean isSaved = cartService.submitCheckoutDetails(checkoutForm);
//        if (!isSaved) {
//            model.addAttribute("error", "Failed to save checkout details. Try again.");
//            return "user/addcart-new";
//        }

        return "redirect:/customer/payment/initiate"; // or any next step
    }
    
}
