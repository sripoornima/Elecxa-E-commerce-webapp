package com.elecxa.controller;

import com.elecxa.dto.OrderDTO;

import com.elecxa.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewAllOrders(HttpSession session,Model model,
                                 @RequestParam(value = "status", required = false, defaultValue = "All") String status , HttpServletRequest request) {
    	
    	
    	if (session.getAttribute("darkMode") == null) {
			session.setAttribute("darkMode", false);
		}
		if (session.getAttribute("sidebarCollapsed") == null) {
			session.setAttribute("sidebarCollapsed", false);
		}

		model.addAttribute("currentUri", request.getRequestURI());

		String token = (String) session.getAttribute("accessToken");
        List<OrderDTO> orders = status.equals("All") 
                                ? orderService.getAllOrders(token)
                                : orderService.getOrdersByStatus(status , token);

       
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", List.of("All", "PLACED", "PENDING", "SHIPPED", "DELIVERED", "CANCELLED"));
        model.addAttribute("selectedStatus", status);
        return "admin/orders";  // Thymeleaf template
    }

    @GetMapping("/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model , HttpSession session) {
		String token = (String) session.getAttribute("accessToken");

        OrderDTO order = orderService.getOrderById(id , token);

        if (order == null) {
            return "redirect:/orders?error=notfound";
        }
        model.addAttribute("order", order);
        return "admin/order-detail";
    }

    @GetMapping("/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id , @RequestParam("status") String status , @RequestParam(value = "filter", required = false, defaultValue = "All") String filter, HttpSession session) {
		String token = (String) session.getAttribute("accessToken");
        orderService.updateOrderStatus(id, status , token);
        return "redirect:/orders";

    }

    @GetMapping("/{id}/invoice")
    public String generateInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("orderId", id);
        return "admin/invoice";
    }
}