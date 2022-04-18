package com.example.janghj.repository;

import com.example.janghj.repository.dto.QUserOrderDto;
import com.example.janghj.repository.dto.UserOrderDto;
import com.example.janghj.repository.dto.UserOrderSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.QOrder.order;
import static com.example.janghj.domain.User.QUser.user;

@Repository
@RequiredArgsConstructor
public class QueryDslUserOrderRepository {

    private final JPAQueryFactory queryFactory;

    public UserOrderDto findOneUserOrder(UserOrderSearchDto userOrderSearchDto) {
        return queryFactory
                .select(new QUserOrderDto(
                        user,
                        order
                ))
                .from(user)
                .join(user.order, order)
                .where(userIdEq(userOrderSearchDto.getUserId()),
                        orderIdEq(userOrderSearchDto.getOrderId()))
                .fetchOne();
    }

    public List<UserOrderDto> findAllUserOrders(UserOrderSearchDto userOrderSearchDto) {
        return queryFactory
                .select(new QUserOrderDto(
                        user,
                        order
                )).from(user)
                .innerJoin(user.order, order)
                .where(userIdEq(userOrderSearchDto.getUserId()))
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? user.id.eq(Long.valueOf(userId)) : null;
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return orderId != null ? order.id.eq(Long.valueOf(orderId)) : null;
    }
}
