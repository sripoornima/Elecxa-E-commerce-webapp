package com.elecxa.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elecxa.dto.AddressDTO;
import com.elecxa.dto.OrderDTO;
import com.elecxa.dto.UserDTO;
import com.elecxa.service.AddressService;
import com.elecxa.service.OrderService;
import com.elecxa.service.ProfileService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AddressService addressService;

    
    @GetMapping
    public String showProfile(Model model , @RequestParam long id , HttpSession session) {
    	String token = (String)session.getAttribute("accessToken");

        UserDTO userProfile = profileService.getUserProfile(id , token);
        AddressDTO userAddress = addressService.getUserAddress(id , token);
        model.addAttribute("address", userAddress);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("activeTab", "profile");
        
        
        List<OrderDTO> order = orderService.getOrdersByCustomerId(id, token);
      
        
        model.addAttribute("ordersCount", order.size());
        model.addAttribute("orders", order);
        model.addAttribute("confirmationTitle", "Thank You for Your Order!");
        model.addAttribute("confirmationMessage", "Your order has been received and is being processed.");
        model.addAttribute("emailConfirmation", "An email confirmation has been sent to your registered email address.");
        List<String> orderSteps = Arrays.asList("Order Placed", "Packed", "Shipped", "Out for Delivery", "Delivered");
        String currentStep = "Shipped"; // Get this from DB dynamically

        model.addAttribute("orderSteps", orderSteps);
        model.addAttribute("currentStep", currentStep);
        
        return "user/profile-new";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("userProfile") UserDTO userProfile,
            @ModelAttribute("address") AddressDTO userAddress,
            BindingResult result,
            HttpSession session,
            Model model) {
    	
    	String token = (String)session.getAttribute("accessToken");

    	 long id = userProfile.getUserId();
                 
        if (result.hasErrors()) {
            model.addAttribute("activeTab", "profile");
            return "user/profile-new";
        }

        profileService.updateUserProfile(userProfile, id , token);
        addressService.updateUserAddress(userAddress, id , token);

        return "redirect:/profile?id=" + id;
    }
}
