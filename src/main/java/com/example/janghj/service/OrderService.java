package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Delivery;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.User;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.repository.ProductRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.OrderProduct;
import com.example.janghj.web.dto.OrderWebDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Order order(UserDetailsImpl nowUser, OrderWebDto orderWebDto) {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다. id  = " + nowUser.getId()));

//        User user = userRepository.findById(1L).orElseThrow(
//                () -> new NullPointerException("해당 유저가 존재하지 않습니다. id  = " + 1L));

        Order order = new Order(user);

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderWebDto.getOrderList().forEach((productId, quantity) ->
                orderProducts.add(createOrder(Long.parseLong((String) productId), (Integer) quantity, order)));
        order.setOrderProduct(orderProducts);

        Delivery delivery = new Delivery(order, user.getAddress());
        order.setDelivery(delivery);
        orderRepository.save(order);

        return order;
    }

    @SneakyThrows // try-catch 기능을 대체해줌
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public OrderProduct createOrder(Long productId, int quantity, Order order) {
        Product product = (Product) productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 상품이 없습니다. productId =" + productId));

        int amount = product.getPrice() * quantity;
        return new OrderProduct(product, product.getPrice(), order, quantity, amount);
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void payForTheOrder(UserDetailsImpl nowUser, Long orderId) {
        findByOrder(orderId);
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void payForTheOrders(UserDetailsImpl nowUser) {

    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void deliveryStart(UserDetailsImpl nowUser) {

    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void deliveryArrive(UserDetailsImpl nowUser) {

    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Order findByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NullPointerException("해당 주문이 존재하지 않습니다. itemId = " + orderId)
        );
        return order;
    }

    public List<Order> findByOrders(UserDetailsImpl nowUser) {
        return orderRepository.findAllByUserId(nowUser.getId());
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void orderCancel(UserDetailsImpl nowUser, Long orderId) {
        Order deleteOrder = orderRepository.findById(orderId).orElseThrow(
                () -> new NullPointerException("해당 주문이 존재하지 않습니다. itemId = " + orderId)
        );

        if (!deleteOrder.getUser().equals(nowUser.getUser())) {
            throw new AccessDeniedException("유저(" + nowUser.getId() + ") 가 다른 유저(" + deleteOrder.getUser().getId() + ")에 접근하여 주문을 삭제하려고 합니다.");
        }

        orderRepository.deleteById(orderId);

    }

}