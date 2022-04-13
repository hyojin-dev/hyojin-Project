package com.example.janghj.repository;

import com.example.janghj.domain.User.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.janghj.domain.User.QUser.user;

@Repository
@RequiredArgsConstructor
public class QueryDslUserRepository {  // QueryDsl Practice class

    private final JPAQueryFactory queryFactory;

    public List<User> findByUsername(String username) {
        return queryFactory
                .selectFrom(user)
                .where(user.username.eq(username))
                .fetch();
    }

    public List<User> findAllUser() {
        return queryFactory
                .selectFrom(user).fetch();
    }

}
