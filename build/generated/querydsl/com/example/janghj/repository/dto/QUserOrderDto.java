package com.example.janghj.repository.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.janghj.repository.dto.QUserOrderDto is a Querydsl Projection type for UserOrderDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserOrderDto extends ConstructorExpression<UserOrderDto> {

    private static final long serialVersionUID = -279541148L;

    public QUserOrderDto(com.querydsl.core.types.Expression<? extends com.example.janghj.domain.User.User> user, com.querydsl.core.types.Expression<? extends com.example.janghj.domain.Order> order) {
        super(UserOrderDto.class, new Class<?>[]{com.example.janghj.domain.User.User.class, com.example.janghj.domain.Order.class}, user, order);
    }

}

