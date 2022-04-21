package com.example.janghj.repository.dto;

import lombok.Getter;

@Getter
public class UserOrderSearchDto {
    private Long userId;
    private Long orderId;

    public UserOrderSearchDto(Long userId, Long orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    public UserOrderSearchDto(Long userId) {
        this.userId = userId;
    }
}
