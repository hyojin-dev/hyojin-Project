package com.example.janghj.web;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.item.*;
import com.example.janghj.repository.ItemRepository;
import com.example.janghj.service.ItemService;
import com.example.janghj.web.dto.ItemDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @Operation(description = "상품 등록하기, 로그인 필요", method = "POST")
    @PostMapping("/item")
    public void registerProduct(@RequestBody ItemDto itemDto) {
        System.out.println(itemDto.getCategory());
        System.out.println(itemDto.getItemColor());
        System.out.println(itemDto.getItemColor().getClass());
        System.out.println(itemDto.getName());
        /* ItemDto.Category 에서 입력 받는 카테고리가 증가할 수록 증가하는 코드
            리팩토링 예정 */
//        if (itemDto.getCategory().toString().equals("TOP")) {
//            Top top = new Top(itemDto);
//            itemRepository.save(top);
//        } else if (itemDto.getCategory().toString().equals("PANTS")) {
//            itemRepository.save(new Pants(itemDto));
//        } else if (itemDto.getCategory().toString().equals("OUTER")) {
//            itemRepository.save(new Outer(itemDto));
//        } else if (itemDto.getCategory().toString().equals("SHOES")) {
//            itemRepository.save(new Shoes(itemDto));
//        } else if (itemDto.getCategory().toString().equals("BAG")) {
//            itemRepository.save(new Bag(itemDto));
//        }
    }

    @Operation(description = "특정 상품 조회하기", method = "GET")
    @GetMapping("/items")
    public List<Item> getProducts(Category category) {
        return itemService.getProducts(category);
    }

    @Operation(description = "상품 삭제하기", method = "DELETE")
    @DeleteMapping("/item")
    public void deleteProduct(@AuthenticationPrincipal UserDetailsImpl nowUser, @PathVariable Long itemId) {
        itemService.deleteProduct(nowUser, itemId);
    }
}
