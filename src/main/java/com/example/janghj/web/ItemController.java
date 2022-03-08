package com.example.janghj.web;

import com.example.janghj.domain.item.Bag;
import com.example.janghj.service.ItemService;
import com.example.janghj.web.dto.ItemDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;

    @Operation(description = "상품 등록하기, 로그인 필요", method = "POST")
    @PostMapping("/items")
    public void registerProduct(@RequestBody ItemDto itemDto) {
        if (itemDto.getCategory().toString().equals("BAG")) {
            System.out.println("Category = BAG");
        }
        itemService.registerProduct(itemDto);
    }
}
