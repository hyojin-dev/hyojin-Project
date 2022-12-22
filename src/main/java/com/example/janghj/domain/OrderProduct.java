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

    private Long productIdValue; // 상품 Id 값
    private int productPriceValue; // 상품 가격
    private int purchaseQuantity; // 주문 수량
    private int amount; // 주문 전체 가격

    public OrderProduct(Product product, Long productIdValue, int productPriceValue, Order order, int purchaseQuantity, int amount) {
        this.product = product;
        this.order = order;
        this.purchaseQuantity = purchaseQuantity;
        this.amount = amount;
        this.productIdValue = productIdValue;
        this.productPriceValue = productPriceValue;
    }
}