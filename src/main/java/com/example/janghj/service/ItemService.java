package com.example.janghj.service;

import com.example.janghj.repository.ItemRepository;
import com.example.janghj.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void registerProduct(ItemDto itemDto) {
        itemRepository.save(itemDto);
    }
}
