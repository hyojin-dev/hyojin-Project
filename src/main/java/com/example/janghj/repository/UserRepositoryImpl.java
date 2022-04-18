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
    /*
    * 객체 조회가 아닌 상황에 따라 필요한 정보들만 조회 할 수 있도록 하자.
    * */
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
                .where(
                        userIdEq(userOrderSearchDto.getUserId()),
                        orderIdEq(userOrderSearchDto.getOrderId())
                )
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
                .where(
                        userIdEq(userOrderSearchDto.getUserId())
                )
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

        // data 더 불러올 때 사용되는 기능
        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .innerJoin(user.order, order)
                .where(
                        userIdEq(userOrderSearchDto.getUserId())
                );
        // contentSize < pageSize && 마지막 페이지일 경우 countQuery 를 실행하지 않는다.
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
