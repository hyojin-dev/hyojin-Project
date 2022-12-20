package com.example.janghj.repository.userCart;

import com.example.janghj.repository.userCart.dto.UserCartDto;

import java.util.List;

public interface UserCartRepositoryCustom {

    List<UserCartDto> findAllByUserCartDto(Long userId);
}
