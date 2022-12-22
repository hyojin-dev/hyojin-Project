package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Delivery;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.OrderProduct;
import com.example.janghj.domain.OrderStatus;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.User;
import com.example.janghj.domain.User.UserCash;
import com.example.janghj.repository.order.OrderRepository;
import com.example.janghj.repository.product.ProductRepository;
import com.example.janghj.web.dto.OrderWebDto;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;

    @Transactional(rollbackFor = Throwable.class)
    public Order order(UserDetailsImpl nowUser, OrderWebDto orderWebDto) throws Throwable {
        Order order = new Order(userService.findByUser(nowUser.getId()));
        order.setOrderProduct(getOrderProduct(order, orderWebDto));

        Delivery delivery = new Delivery(order, orderWebDto.getAddress());
        order.setDelivery(delivery);

        orderRepository.save(order);
        return order;
    }

    public List<OrderProduct> getOrderProduct(Order order, OrderWebDto orderWebDto) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderWebDto.getOrderList().forEach((productId, quantity) -> {
            try {
                orderProducts.add(createOrderProduct(Long.parseLong((String) productId), (Integer) quantity, order));
            } catch (Throwable e) {
                throw new NullPointerException("해당 상품이 존재하지 않거나 재고가 부족합니다. productId = " + productId);
            }
        });
        return orderProducts;
    }

    public OrderProduct createOrderProduct(Long productId, int quantity, Order order) throws Throwable {
        Product product = (Product) productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 상품이 없습니다. productId =" + productId));

        product.salesQuantity(quantity);
        int amount = product.getPrice() * quantity;
        return new OrderProduct(product, productId, product.getPrice(), order, quantity, amount);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Order updateOrder(UserDetailsImpl nowUser, Long orderId, OrderWebDto orderWebDto) throws Throwable {
        Order order = findByOrder(nowUser, orderId);
        List<OrderProduct> orderProducts = getOrderProduct(order, orderWebDto);
        order.setOrderProduct(orderProducts);

        if (orderWebDto.getAddress() != null) {
            order.setAddress(orderWebDto.getAddress());
        }
        orderRepository.save(order);

        return order;
    }

    public Order findByOrder(UserDetailsImpl nowUser, Long orderId) {
        if (!nowUser.getUser().equals(nowUser.getUser())) {
            throw new AccessDeniedException("유저(" + nowUser.getId() + ") 가 " +
                    "다른 유저(" + nowUser.getUser().getId() + ")에 접근하려고 합니다.");
        }
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NullPointerException("해당 주문이 존재하지 않습니다. itemId = " + orderId)
        );
        return order;
    }

    public List<Order> findByOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Order payForTheOrder(UserDetailsImpl nowUser, Long orderId) throws Exception {
        Order order = findByOrder(nowUser, orderId);
        User user = userService.findByUser(nowUser.getId());

        canIBuyThis(user.getUserCash(), order);
        user.payForIt(order.getTotalAmount());
        order.setPaymentCompleted();

        orderRepository.save(order);
        userService.saveUser(user);
        return order;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void payForTheOrders(UserDetailsImpl nowUser) {
        List<Order> getOrders = findByDeliveryStatusOrders(nowUser.getId(), OrderStatus.WaitingForPayment);
        User user = userService.findByUser(nowUser.getId());

        UserCash userCash = user.getUserCash();
        int totalAmount = setOrder(userCash, getOrders);
        user.payForIt(totalAmount);
    }

    public int setOrder(UserCash userCash, List<Order> orders) {
        int totalAmount = 0;
        for (Order order : orders) {
            totalAmount += order.getTotalAmount();
            order.getDelivery().setStatus();
            order.setPaymentCompleted();
        }
        canIBuyThis(userCash, totalAmount);

        return totalAmount;
    }

    public List<Order> findByDeliveryStatusOrders(Long userId, OrderStatus orderStatus) {
        return orderRepository.findAllByUserIdAndOrderStatus(userId, orderStatus);
    }

    public Boolean canIBuyThis(UserCash userCash, Order order) {
        if (userCash.getMoney() < order.getTotalAmount()) {
            throw new ArithmeticException(
                    "보유한 금액(" + userCash.getMoney() + ")보다 상품의 가격(" + order.getTotalAmount() + ")이 높습니다.");
        }
        return true;
    }

    public Boolean canIBuyThis(UserCash userCash, int price) {
        if (userCash.getMoney() < price) {
            throw new ArithmeticException(
                    "보유한 금액(" + userCash.getMoney() + ")보다 상품의 가격(" + price + ")이 높습니다.");
        }
        return true;
    }

    public void cancelOrder(UserDetailsImpl nowUser, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NullPointerException("해당 주문이 존재하지 않습니다. orderId = " + orderId)
        );
        if (order.getUser().getId() != nowUser.getId()) {
            throw new AccessDeniedException("유저(" + nowUser.getId() + ") 가 " +
                    "다른 유저(" + order.getUser().getId() + ")의 Order 에 접근하고 있습니다.");
        }
        List<Product> addProduct = new ArrayList<>();
        order.getOrderProduct().forEach(orderProduct -> {
            addProduct.add(cancelOrderProduct(orderProduct.getProduct(), orderProduct.getPurchaseQuantity()));
        });
        productRepository.saveAll(addProduct);
        orderRepository.deleteById(orderId);
    }

    private Product cancelOrderProduct(Product product, int quantity) {
        product.addQuantity(quantity);
        return product;
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void deliveryStart(UserDetailsImpl nowUser) {
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public void orderDeliveryArrived(UserDetailsImpl nowUser) {
    }
}