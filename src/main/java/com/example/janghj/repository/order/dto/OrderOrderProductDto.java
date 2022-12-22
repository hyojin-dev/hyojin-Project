package com.example.janghj.repository.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderOrderProductDto {
    private Long orderProductID;
    private Long productId;
    private int productPrice;
    private int count;
    private int amount;

    @QueryProjection
    public OrderOrderProductDto(Long orderProductID, Long productId, int productPrice, int count, int amount) {
        this.orderProductID = orderProductID;
        this.productId = productId;
        this.productPrice = productPrice;
        this.count = count;
        this.amount = amount;
    }
}
