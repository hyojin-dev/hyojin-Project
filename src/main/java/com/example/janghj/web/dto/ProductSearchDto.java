package com.example.janghj.web.dto;

import com.example.janghj.domain.Category;
import lombok.Getter;

@Getter
public class ProductSearchDto {
    Category category;
    String sort;
}
