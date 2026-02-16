package com.elecxa.service;


import com.elecxa.model.CartItem;
import com.elecxa.model.Order;
import com.elecxa.model.OrderStatus;
import com.elecxa.model.Product;
import com.elecxa.repository.CartItemRepository;

import com.elecxa.repository.OrderRepository;
import com.elecxa.repository.ProductRepository;
import com.elecxa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartItemRepository cartitemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    
      public Order placeOrder(Long userId, Long cartId, BigDecimal totalAmount, Long productId) {
    	  
    	if(productId == -1) {
    		List<CartItem> products = cartService.getCartItems(cartId);
    		Order returnorder = new Order() ;
    		for(CartItem items : products) {
    			Order order = new Order();
    	        order.setUser(userRepository.findById(userId).orElse(null));
    	        order.setProduct(productRepository.findById(items.getProduct().getProductId()).orElse(null));
    	        order.setOrderedDate(LocalDateTime.now());
    	        order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(3));
    	        BigDecimal price = items.getProduct().getPrice().subtract(((items.getProduct().getDiscount().divide(new BigDecimal(100)))).multiply(items.getProduct().getPrice()));
    	        order.setTotalAmount(price);
    	        order.setOrderStatus(OrderStatus.PLACED);
    	        returnorder = orderRepository.save(order);
    		}
    		return returnorder;
    	}
        Order order = new Order();
        order.setUser(userRepository.findById(userId).orElse(null));
        order.setProduct(productRepository.findById(productId).orElse(null));
        order.setOrderedDate(LocalDateTime.now());
        order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(3));
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.PLACED);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }


    public List<Order> getOrdersByProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return orderRepository.findByProduct(product);
    }

    public List<Order> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByOrderedDateBetween(start, end);
    }

    // ✅ New method for combined filter
    public List<Order> getOrdersByStatusAndDateRange(OrderStatus status, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByOrderStatusAndOrderedDateBetween(status, start, end);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public long countOrdersByStatus(OrderStatus status) {
        return orderRepository.countByOrderStatus(status);
    }

    public BigDecimal calculateTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }

    public long getTotalOrderCount() {
        return orderRepository.count();
    }

    public List<Order> getRecentOrders() {
        return orderRepository.findAll().reversed().subList(0, 10);
    }

    public List<Double> getRevenueChartData() {
        return orderRepository.findMonthlyRevenue();
    }

	public List<Order> getOrdersByUser(long customerId) {
		return orderRepository.findByUser_UserId(customerId);
	}

	public Order cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).get();
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}
}
