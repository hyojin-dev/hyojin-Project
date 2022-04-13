package com.example.janghj.repository;

import com.example.janghj.domain.DeliveryStatus;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);
    List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);


}
