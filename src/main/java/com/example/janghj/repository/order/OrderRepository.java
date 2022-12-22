package com.example.janghj.repository.order;

import com.example.janghj.domain.Order;
import com.example.janghj.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    @Query("SELECT distinct o FROM Order o join fetch o.orderProduct WHERE o.user.id = :userId")
    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
