package com.example.janghj.repository;

import com.example.janghj.domain.User.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.User.QUser.user;

@Repository
public class UserRepositoryImpl implements UserRepositoyCustom {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<User> findAllUser() {
        return queryFactory
                .selectFrom(user).fetch();
    }

}
