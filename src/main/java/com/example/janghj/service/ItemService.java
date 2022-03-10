package com.example.janghj.service;

import com.example.janghj.config.security.UserDetailsImpl;
import com.example.janghj.domain.Category;
import com.example.janghj.domain.User.UserRole;
import com.example.janghj.domain.item.Item;
import com.example.janghj.repository.ItemRepository;
import com.example.janghj.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void registerProduct(Item item) {
        itemRepository.save(item);
    }

    public List<Item> getProducts(Category category) {
        return itemRepository.findAllByCategory(category);
    }

    public void deleteProduct(UserDetailsImpl nowUser, Long itemId) {
        if (!nowUser.getUserRole().equals(UserRole.ADMIN)) {
            return;
        }
        itemRepository.deleteById(itemId);
    }
}
