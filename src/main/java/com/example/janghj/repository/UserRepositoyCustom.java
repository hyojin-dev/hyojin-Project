package com.example.janghj.repository;

import com.example.janghj.domain.User.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllUser();
}
