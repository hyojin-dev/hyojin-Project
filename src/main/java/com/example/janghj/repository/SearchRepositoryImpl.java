package com.example.janghj.repository;

import com.example.janghj.domain.Product.QProduct;
import com.example.janghj.domain.QOrder;
import com.example.janghj.domain.User.QUser;
import com.example.janghj.repository.dto.QUserOrderProductDto;
import com.example.janghj.repository.dto.UserOrderProductDto;
import com.example.janghj.repository.dto.UserOrderProductSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.Product.QProduct.product;
import static com.example.janghj.domain.QOrder.order;
import static com.example.janghj.domain.QOrderProduct.orderProduct;
import static com.example.janghj.domain.User.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class SearchRepositoryImpl {
    /*
     * 객체 조회가 아닌 상황에 따라 필요한 정보들만 조회 할 수 있도록 하자.
     * */
    private final JPAQueryFactory queryFactory;

    public SearchRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private BooleanExpression userEq(String user) {
        return hasText(user) ? QUser.user.username.eq(user) : null;
    }

    private BooleanExpression orderEq(String order) {
        return hasText(order) ? QOrder.order.user.username.eq(order) : null;
    }

    private BooleanExpression orderProductEq(String orderProduct) {
        return hasText(orderProduct) ? product.name.eq(orderProduct) : null;
    }

    private BooleanExpression productEq(String product) {
        return hasText(product) ? QProduct.product.name.eq(product) : null;
    }

    public List<UserOrderProductDto> SearchUserOrderProduct(UserOrderProductSearchDto userOrderProductSearchDto) {
        List<UserOrderProductDto> fetch = queryFactory
                .select(new QUserOrderProductDto(
                        user,
                        order,
                        orderProduct,
                        product
                )).from(user, product)
                .innerJoin(user.order, order)
                .innerJoin(order.orderProduct, orderProduct)
                .innerJoin(product)
                .where(
                        userEq(userOrderProductSearchDto.getUserSearch()),
                        orderEq(userOrderProductSearchDto.getOrderSearch()),
                        orderProductEq(userOrderProductSearchDto.getOrderProductSearch()),
                        productEq(userOrderProductSearchDto.getProduct())
                )
                .fetch();
        return fetch;
    }
}
