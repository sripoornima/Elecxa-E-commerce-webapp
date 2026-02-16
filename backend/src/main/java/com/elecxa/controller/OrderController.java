package com.elecxa.controller;

import com.elecxa.model.Order;

import com.elecxa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestParam Long userId,
                                            @RequestParam Long cartId,
                                            @RequestParam BigDecimal totalAmount,
                                            @RequestParam Long productId) {
        return ResponseEntity.ok(orderService.placeOrder(userId, cartId, totalAmount , productId));                                      
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @GetMapping("/total-orders")
    public ResponseEntity<Long> getTotalOrderCount() {
        return ResponseEntity.ok(orderService.getTotalOrderCount());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Order>> getRecentOrders() {
        return ResponseEntity.ok(orderService.getRecentOrders());
    }
    

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(customerId));
    }
    

    @GetMapping("/revenue-data")
    public ResponseEntity<List<Double>> getRevenueChartData() {
        return ResponseEntity.ok(orderService.getRevenueChartData());
    }
    
    @GetMapping("/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
    	BigDecimal revenue = orderService.calculateTotalRevenue();
    	revenue = revenue == null ? new BigDecimal(0) : revenue;
        return ResponseEntity.ok(revenue);
    }
    
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));                                      
    }

}
