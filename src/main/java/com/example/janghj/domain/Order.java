package com.example.janghj.domain;

import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.User;
import com.example.janghj.web.dto.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderProducts = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    public Order(User user, Delivery delivery) {
        this.user = user;
        this.delivery = delivery;
    }

    //    Test 용도

    public Order(List<OrderItem> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
