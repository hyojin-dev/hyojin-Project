package com.example.janghj.web.dto;

import com.example.janghj.domain.Address;
import com.example.janghj.domain.User.HowToPay;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OrderWebDto {
    Map orderList = new ConcurrentHashMap<String, Integer>();
    HowToPay howToPay; // CASH, Mileage
}
