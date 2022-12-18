package com.example.janghj.domain;

import com.example.janghj.domain.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @Id
    @GeneratedValue
    @Column(name = "orderProduct_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량
    private int amount; // 주문 전체 가격

    public OrderProduct(Product product, int orderPrice, Order order, int count, int amount) {
        this.product = product;
        this.orderPrice = orderPrice;
        this.order = order;
        this.count = count;
        this.amount = amount;
    }
}