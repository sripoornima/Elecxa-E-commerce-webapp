package com.elecxa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elecxa.service.OrderService;
import com.elecxa.service.ProductService;
import com.elecxa.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class DashboardController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model ,HttpSession session,HttpServletRequest request) {
    	
    	
    	if (session.getAttribute("darkMode") == null) {
			session.setAttribute("darkMode", false);
		}
		if (session.getAttribute("sidebarCollapsed") == null) {
			session.setAttribute("sidebarCollapsed", false);
		}
		model.addAttribute("currentUri", request.getRequestURI());

		String token = (String) session.getAttribute("accessToken");
        model.addAttribute("totalRevenue", orderService.getTotalRevenue(token));
        System.out.println("total : " + orderService.getTotalRevenue(token));
        model.addAttribute("totalOrders", orderService.getTotalOrderCount(token));
        model.addAttribute("totalProducts", productService.getTotalProductCount(token));
        model.addAttribute("totalCustomers", userService.getTotalCustomerCount(token));
        
        model.addAttribute("recentOrders", orderService.getRecentOrders(token));
        model.addAttribute("topProducts", productService.getTopSellingProducts(token));
        
        model.addAttribute("revenueData", orderService.getRevenueChartData(token));
 
        return "admin/dashboard"; // your Thymeleaf path
    }
}
