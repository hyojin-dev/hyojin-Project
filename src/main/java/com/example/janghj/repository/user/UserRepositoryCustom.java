package com.example.janghj.repository.user;

import com.example.janghj.domain.User.User;
import com.example.janghj.repository.dto.UserOrderDto;
import com.example.janghj.repository.dto.UserOrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllUser();
    UserOrderDto findOneUserOrder(UserOrderSearchDto userOrderSearchDto);
    List<UserOrderDto> findAllUserOrders(UserOrderSearchDto userOrderSearchDto);
    Page<UserOrderDto> findPageUserOrders(UserOrderSearchDto userOrderSearchDto, Pageable pageable);
}
