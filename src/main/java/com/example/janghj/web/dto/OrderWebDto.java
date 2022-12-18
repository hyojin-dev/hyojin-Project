package com.example.janghj.web.dto;

import com.example.janghj.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OrderWebDto {
    Map orderList;
    Address address;

    public OrderWebDto(Map<String, Integer> orderList, Address address) {
        this.orderList = orderList;
        this.address = address;
    }
}
