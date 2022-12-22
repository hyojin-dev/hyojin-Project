package com.example.janghj.repository.order.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.janghj.repository.order.dto.QOrderOrderProductDto is a Querydsl Projection type for OrderOrderProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderOrderProductDto extends ConstructorExpression<OrderOrderProductDto> {

    private static final long serialVersionUID = 570941656L;

    public QOrderOrderProductDto(com.querydsl.core.types.Expression<Long> orderProductID, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<Integer> productPrice, com.querydsl.core.types.Expression<Integer> count, com.querydsl.core.types.Expression<Integer> amount) {
        super(OrderOrderProductDto.class, new Class<?>[]{long.class, long.class, int.class, int.class, int.class}, orderProductID, productId, productPrice, count, amount);
    }

}

