package com.example.janghj.repository.userCart.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.janghj.repository.userCart.dto.QUserCartDto is a Querydsl Projection type for UserCartDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserCartDto extends ConstructorExpression<UserCartDto> {

    private static final long serialVersionUID = -1084593861L;

    public QUserCartDto(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> productName, com.querydsl.core.types.Expression<Integer> productPrice) {
        super(UserCartDto.class, new Class<?>[]{long.class, String.class, long.class, String.class, int.class}, userId, userName, productId, productName, productPrice);
    }

}

