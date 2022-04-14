package com.example.janghj.repository.dto;

import com.example.janghj.domain.Order;
import com.example.janghj.domain.User.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserOrderDto {
    User user;
    Order order;

    @QueryProjection
    public UserOrderDto(User user, Order order) {
        this.user = user;
        this.order = order;
    }
}
