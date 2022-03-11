package com.example.janghj.domain;

import com.example.janghj.domain.User.User;
import com.example.janghj.domain.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private int orderPrice; //주문 가격

    private int count; //주문 수량

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @JsonIgnore
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<Product> orderProducts = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public Order(User user, int orderPrice, int count, Delivery delivery) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.user = user;
//        this.orderProducts = orderProducts;
        this.delivery = delivery;
    }
}
