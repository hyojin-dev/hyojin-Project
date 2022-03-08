package com.example.janghj.web.dto;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class OrderDto {
    // 동시성 이슈 HashMap -> ConcurrentHashMap 변경
    HashMap orderList = new HashMap<String, Integer>();
}
