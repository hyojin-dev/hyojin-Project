package com.example.janghj.repository.dto;

import lombok.Getter;

@Getter
public class UserOrderProductSearchDto {
    private String userSearch;
    private String orderSearch;
    private String orderProductSearch;
    private String product;

    public UserOrderProductSearchDto(String userSearch, String orderSearch, String orderProductSearch, String product) {
        this.userSearch = userSearch;
        this.orderSearch = orderSearch;
        this.orderProductSearch = orderProductSearch;
        this.product = product;
    }
}
