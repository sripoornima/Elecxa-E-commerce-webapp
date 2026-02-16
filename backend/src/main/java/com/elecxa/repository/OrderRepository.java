package com.elecxa.repository;

import com.elecxa.model.Order;
import com.elecxa.model.OrderStatus;
import com.elecxa.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderedDateBetween(LocalDateTime start, LocalDateTime end);

    long countByOrderStatus(OrderStatus status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = 'COMPLETED'")
    BigDecimal calculateTotalRevenue();

    @Query("SELECT FUNCTION('MONTH', o.orderedDate) AS month, SUM(o.totalAmount) FROM Order o " +
           "WHERE o.orderStatus = 'COMPLETED' GROUP BY FUNCTION('MONTH', o.orderedDate) ORDER BY month")
    List<Double> findMonthlyRevenue();

    List<Order> findByProduct(Product product);

	List<Order> findByUser_UserId(long id);

    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByOrderStatusAndOrderedDateBetween(OrderStatus status, LocalDateTime start, LocalDateTime end);
}
