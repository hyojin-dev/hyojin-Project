package com.example.janghj.repository;

import com.example.janghj.domain.item.Item;
import com.example.janghj.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public void save(ItemDto itemDto) {

    }

}
