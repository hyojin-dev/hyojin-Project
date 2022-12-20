package com.example.janghj.repository.product;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository<T extends Product> extends JpaRepository<T, Long> {
    List<Product> findAllByCategory(Category category);
    List<Product> findAllByCategory(Category category, Sort sort);
    Product findByName(String name);
}
