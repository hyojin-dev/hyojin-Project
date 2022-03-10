package com.example.janghj.repository;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategory(Category category);

}
