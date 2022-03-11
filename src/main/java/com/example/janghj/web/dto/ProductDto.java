package com.example.janghj.web.dto;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.ProductColor;
import lombok.Getter;

@Getter
public class ProductDto {
    String name;
    int price;
    int stockQuantity;
    Category category; // TOP, PANTS, OUTER, SHOES, BAG
    ProductColor productColor; // RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE

    int topSize;
    int pantsSize;
    int shoesSize;
}
