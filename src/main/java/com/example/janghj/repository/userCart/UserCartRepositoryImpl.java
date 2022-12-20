package com.example.janghj.repository.userCart;

import com.example.janghj.domain.User.QUserCart;
import com.example.janghj.repository.userCart.dto.QUserCartDto;
import com.example.janghj.repository.userCart.dto.UserCartDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.Product.QProduct.product;
import static com.example.janghj.domain.User.QUser.user;

@Repository
public class UserCartRepositoryImpl implements UserCartRepositoryCustom {

    /*
     * 객체 조회가 아닌 상황에 따라 필요한 정보들만 조회 할 수 있도록 하자.
     * */
    private final JPAQueryFactory queryFactory;

    public UserCartRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? user.id.eq(Long.valueOf(userId)) : null;
    }

    @Override
    public List<UserCartDto> findAllByUserCartDto(Long userId) {
        List<UserCartDto> fetch = queryFactory
                .select(new QUserCartDto(
                        user.id,
                        user.username,
                        product.id,
                        product.name,
                        product.price
                )).from(QUserCart.userCart)
                .innerJoin(QUserCart.userCart.user, user)
                .innerJoin(QUserCart.userCart.product, product)
                .where(userIdEq(userId))
                .fetch();
        return fetch;
    }
}
