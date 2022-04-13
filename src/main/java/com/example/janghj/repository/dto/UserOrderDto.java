package com.example.janghj.repository.dto;

import com.example.janghj.domain.Address;
import com.example.janghj.domain.OrderStatus;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.web.dto.OrderProduct;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class UserOrderDto {

    private Long userId;
    private Long kakaoId;
    private String username;
    private String email;
    private UserRole userRole;
    private Address address;

    private Long orderId;
    private Integer totalAmount;
    private List<OrderProduct> orderProduct;
    private OrderStatus orderStatus;

    @QueryProjection
    public UserOrderDto(Long userId, Long kakaoId, String username, String email, UserRole userRole, Address address, Long orderId, Integer totalAmount, List<OrderProduct> orderProduct, OrderStatus orderStatus) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.username = username;
        this.email = email;
        this.userRole = userRole;
        this.address = address;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.orderProduct = orderProduct;
        this.orderStatus = orderStatus;
    }
}
