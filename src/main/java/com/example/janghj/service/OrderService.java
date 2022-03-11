package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Delivery;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.User;
import com.example.janghj.repository.DeliveryRepository;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.OrderItem;
import com.example.janghj.web.dto.OrderWebDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;


    public void order(OrderWebDto orderWebDto) {
        List<OrderItem> productList = new ArrayList<>();

        orderWebDto.getOrderList().forEach((productId, quantity) ->
                productList.add(createOrder(Long.parseLong((String) productId), (Integer) quantity)));

        Order order = new Order(productList);
        Delivery delivery = new Delivery(order);

//        orderDto.getOrderList().forEach((productId, quantity) ->
//                productList.add((Product) productRepository.getById(productId))
//        );

//        orderDto.getOrderList().forEach((productId, quantity) ->
//                createOrder(Long.parseLong((String) productId), (Integer) quantity));
    }

    public OrderItem createOrder(Long productId, int quantity) {
        Product product = (Product) productRepository.getById(productId);
        return new OrderItem(product, quantity);
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
