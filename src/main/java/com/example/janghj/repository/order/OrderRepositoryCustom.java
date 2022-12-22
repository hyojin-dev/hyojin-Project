package com.example.janghj.repository.order;

import com.example.janghj.repository.order.dto.OrderOrderProductDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderOrderProductDto> findAllByUserOrders(Long userId);
}
