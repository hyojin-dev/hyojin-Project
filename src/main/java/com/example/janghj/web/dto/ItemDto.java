package com.example.janghj.web.dto;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.item.Item;
import com.example.janghj.domain.item.ItemColor;
import lombok.Getter;

@Getter
public class ItemDto {
    String name;
    int price;
    int stockQuantity;
    ItemColor itemColor;
    Category category;
    Item item;

}
