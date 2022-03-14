package com.example.janghj.web.dto;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OrderWebDto {
    Map orderList = new ConcurrentHashMap<String, Integer>();
}
