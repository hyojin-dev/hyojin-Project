package com.example.janghj.repository.order;

import com.example.janghj.repository.order.dto.OrderOrderProductDto;
import com.example.janghj.repository.order.dto.QOrderOrderProductDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.Product.QProduct.product;
import static com.example.janghj.domain.QOrder.order;
import static com.example.janghj.domain.QOrderProduct.orderProduct;
import static com.example.janghj.domain.User.QUser.user;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    /*
     * 객체 조회가 아닌 상황에 따라 필요한 정보들만 조회 할 수 있도록 하자.
     * */
    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? user.id.eq(userId) : null;
    }

    @Override
    public List<OrderOrderProductDto> findAllByUserOrders(Long userId) {
        return queryFactory
                .select(new QOrderOrderProductDto(
                        orderProduct.id,
                        orderProduct.product.id,
                        orderProduct.product.price,
                        orderProduct.purchaseQuantity,
                        orderProduct.amount
                ))
                .from(order)
                .innerJoin(order.orderProduct, orderProduct)
                .innerJoin(orderProduct.product, product)
                .where(
                        userIdEq(userId)
                )
                .fetch();
    }
}
