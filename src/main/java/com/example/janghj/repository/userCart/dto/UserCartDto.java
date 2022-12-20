package com.example.janghj.repository.userCart.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCartDto {
    Long userId;
    String userName;
    Long productId;
    String productName;
    int productPrice;

    @QueryProjection
    public UserCartDto(Long userId, String userName, Long productId, String productName, int productPrice) {
        this.userId = userId;
        this.userName = userName;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
