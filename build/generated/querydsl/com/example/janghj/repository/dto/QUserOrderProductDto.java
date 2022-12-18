package com.example.janghj.repository.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.janghj.repository.dto.QUserOrderProductDto is a Querydsl Projection type for UserOrderProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserOrderProductDto extends ConstructorExpression<UserOrderProductDto> {

    private static final long serialVersionUID = -63491509L;

    public QUserOrderProductDto(com.querydsl.core.types.Expression<? extends com.example.janghj.domain.User.User> user, com.querydsl.core.types.Expression<? extends com.example.janghj.domain.Order> order, com.querydsl.core.types.Expression<? extends com.example.janghj.domain.OrderProduct> orderProduct, com.querydsl.core.types.Expression<? extends com.example.janghj.domain.Product.Product> product) {
        super(UserOrderProductDto.class, new Class<?>[]{com.example.janghj.domain.User.User.class, com.example.janghj.domain.Order.class, com.example.janghj.domain.OrderProduct.class, com.example.janghj.domain.Product.Product.class}, user, order, orderProduct, product);
    }

}

