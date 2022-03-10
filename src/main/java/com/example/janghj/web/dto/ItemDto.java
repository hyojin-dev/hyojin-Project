package com.example.janghj.web.dto;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.item.ItemColor;
import lombok.Getter;

@Getter
public class ItemDto {
    String name;
    int price;
    int stockQuantity;
    Category category; // TOP, PANTS, OUTER, SHOES, BAG
    ItemColor itemColor; // RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE

    int topSize;
    int pantsSize;
    int outerSize;
    int shoesSize;
    int bagSize;


}
