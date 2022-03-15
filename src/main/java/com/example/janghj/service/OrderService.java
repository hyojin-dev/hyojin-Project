package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Delivery;
import com.example.janghj.domain.DeliveryStatus;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserCash;
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

        Order order = new Order(user);

        List<OrderProduct> orderProducts = getOrderProduct(order, orderWebDto);
        order.setOrderProduct(orderProducts);

        Delivery delivery = new Delivery(order, orderWebDto.getAddress());
        order.setDelivery(delivery);
        orderRepository.save(order);

        return order;
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Order updateOrder(UserDetailsImpl nowUser, Long orderId, OrderWebDto orderWebDto) {
        Order order = findByOrder(nowUser, orderId);
        List<OrderProduct> orderProducts = getOrderProduct(order, orderWebDto);
        order.setOrderProduct(orderProducts);

        if (orderWebDto.getAddress() != null) {
            order.setAddress(orderWebDto.getAddress());
        }
        orderRepository.save(order);

        return order;
    }

    public List<OrderProduct> getOrderProduct(Order order, OrderWebDto orderWebDto) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderWebDto.getOrderList().forEach((productId, quantity) ->
                orderProducts.add(createOrder(Long.parseLong((String) productId), (Integer) quantity, order)));
        return orderProducts;
    }

    @SneakyThrows // 예외 처리 기능
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public OrderProduct createOrder(Long productId, int quantity, Order order) {
        Product product = (Product) productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 상품이 없습니다. productId =" + productId));

        int amount = product.getPrice() * quantity;
        return new OrderProduct(product, product.getPrice(), order, quantity, amount);
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Order payForTheOrder(UserDetailsImpl nowUser, Long orderId) throws Exception {
        Order order = findByOrder(nowUser, orderId);
        User user = nowUser.getUser();

        canIBuyThis(user.getUserCash(), order);

        user.payForIt(order.getTotalAmount());

        order.getDelivery().setStatus(DeliveryStatus.PaymentCompleted);
        return order;
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void payForTheOrders(UserDetailsImpl nowUser) {
        int totalAmount = 0;
        List<Order> orders = findByOrders(nowUser);
        User user = nowUser.getUser();

        for (Order order : orders) {
            totalAmount += order.getTotalAmount();
            order.getDelivery().setStatus(DeliveryStatus.PaymentCompleted);
            canIBuyThis(user.getUserCash(), order);
        }
        user.payForIt(totalAmount);
    }

    public Boolean canIBuyThis(UserCash userCash, Order order) {
        if (userCash.getMoney() < order.getTotalAmount()) {
            throw new ArithmeticException();
        }
        return true;
    }


    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void deliveryStart(UserDetailsImpl nowUser) {

    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void orderDeliveryArrived(UserDetailsImpl nowUser) {

    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Order findByOrder(UserDetailsImpl nowUser, Long orderId) {
        if (!nowUser.getUser().equals(nowUser.getUser())) {
            throw new AccessDeniedException("유저(" + nowUser.getId() + ") 가 다른 유저(" + nowUser.getUser().getId() + ")에 접근하려고 합니다.");
        }

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
        orderRepository.findById(orderId).orElseThrow(
                () -> new NullPointerException("해당 주문이 존재하지 않습니다. orderId = " + orderId)
        );

        orderRepository.deleteById(orderId);
    }
}