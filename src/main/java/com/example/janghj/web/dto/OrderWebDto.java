package com.example.janghj.web.dto;

import com.example.janghj.domain.Address;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OrderWebDto {
    Map orderList = new ConcurrentHashMap<String, Integer>();
    Address address;

    public OrderWebDto(Map orderList, Address address) {
        this.orderList = orderList;
        this.address = address;
    }
}
