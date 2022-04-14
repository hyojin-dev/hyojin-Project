package com.example.janghj.domain;

import com.example.janghj.domain.User.User;
import com.example.janghj.web.dto.OrderProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private int totalAmount;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProduct;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // WaitingForPayment, PaymentCompleted,

    public Order(int totalAmount, User user, List<OrderProduct> orderProduct, Delivery delivery, OrderStatus orderStatus) {
        this.totalAmount = totalAmount;
        this.user = user;
        this.orderProduct = orderProduct;
        this.delivery = delivery;
        this.orderStatus = orderStatus;
    }

    public Order(User user) {
        this.user = user;
        this.orderStatus = OrderStatus.WaitingForPayment;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
        for (OrderProduct orderPrice : orderProduct) {
            this.totalAmount += orderPrice.getAmount();
        }
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setAddress(Address address) {
        this.delivery.setAddress(address);
    }

    public void setPaymentCompleted() {
        this.orderStatus = OrderStatus.PaymentCompleted;
    }
}
