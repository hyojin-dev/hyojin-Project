package com.example.janghj.web.dto;

import com.example.janghj.domain.Category;
import lombok.Getter;

@Getter
public class ProductSearchDto {
    Category category; // 상품 종류
    String sort; // 1.null, 2.date 날짜순
}
