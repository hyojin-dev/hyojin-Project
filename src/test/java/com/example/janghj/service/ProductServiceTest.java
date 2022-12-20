package com.example.janghj.service;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.Product.ProductColor;
import com.example.janghj.repository.product.ProductRepository;
import com.example.janghj.web.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    ProductDto productDto;
    ProductDto productDto2;

    @BeforeEach
    void beforeEach() {
        this.productDto = new ProductDto("productName", 1000, 10, Category.TOP, ProductColor.RED, 13);
        this.productDto2 = new ProductDto("productName2", 10000, 20, Category.SHOES, ProductColor.RED, 13);
    }

    @Test
    @DisplayName("상품 정보 저장하기 성공")
    void getProducts(Category category, String sort) {
        // given

        // when
        productService.registerProduct(productDto);

        // then
    }
}
