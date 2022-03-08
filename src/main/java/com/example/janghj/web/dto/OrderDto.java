package com.example.janghj.web.dto;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OrderDto {
    // 동시성 이슈 HashMap -> ConcurrentHashMap 변경
    Map orderList = new ConcurrentHashMap<String, Integer>();
}
