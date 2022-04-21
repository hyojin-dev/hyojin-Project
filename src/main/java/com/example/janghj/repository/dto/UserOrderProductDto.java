package com.example.janghj.repository.dto;

import com.example.janghj.domain.Order;
import com.example.janghj.domain.OrderProduct;
import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.User.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserOrderProductDto {
    private User user;
    private Order order;
    private OrderProduct orderProduct;
    private Product product;

    @QueryProjection
    public UserOrderProductDto(User user, Order order, OrderProduct orderProduct, Product product) {
        this.user = user;
        this.order = order;
        this.orderProduct = orderProduct;
        this.product = product;
    }
}
