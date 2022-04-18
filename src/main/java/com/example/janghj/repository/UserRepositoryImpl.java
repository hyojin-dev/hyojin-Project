package com.example.janghj.repository;

import com.example.janghj.domain.User.User;
import com.example.janghj.repository.dto.QUserOrderDto;
import com.example.janghj.repository.dto.UserOrderDto;
import com.example.janghj.repository.dto.UserOrderSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.QOrder.order;
import static com.example.janghj.domain.User.QUser.user;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? user.id.eq(Long.valueOf(userId)) : null;
    }

    private BooleanExpression orderIdEq(Long orderId) {
        return orderId != null ? order.id.eq(Long.valueOf(orderId)) : null;
    }

    @Override
    public List<User> findAllUser() {
        return queryFactory
                .selectFrom(user).fetch();
    }

    @Override
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

    @Override
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

    @Override
    public Page<UserOrderDto> findPageUserOrders(UserOrderSearchDto userOrderSearchDto, Pageable pageable) {
        List<UserOrderDto> results = queryFactory
                .select(new QUserOrderDto(
                        user,
                        order
                )).from(user)
                .innerJoin(user.order, order)
                .where(
                        userIdEq(userOrderSearchDto.getUserId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .innerJoin(user.order, order)
                .where(userIdEq(userOrderSearchDto.getUserId())
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
