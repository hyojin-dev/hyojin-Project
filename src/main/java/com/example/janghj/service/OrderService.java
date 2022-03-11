package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.repository.DeliveryRepository;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    public void order(OrderDto orderDto) {
        Queue orderList = new ConcurrentLinkedQueue<Product>();


        orderDto.getOrderList().forEach((key, value) ->
                orderList.offer(orderRepository.findById(Long.parseLong(key.toString())).orElseThrow(
                        () -> new NullPointerException("해당 주문이 존재하지 않습니다. itemId = " + key)
                )));
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NullPointerException("해당 주문이 존재하지 않습니다. itemId = " + orderId)
        );
    }

    public List<Order> getOrders(UserDetailsImpl nowUser) {
        return orderRepository.findAllByUserId(nowUser.getId());
    }

    public void orderCancel(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
