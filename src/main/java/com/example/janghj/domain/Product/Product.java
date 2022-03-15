package com.example.janghj.domain.Product;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Timestamped;
import com.example.janghj.web.dto.ProductDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Product extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")// 반드시 값을 가지도록 합니다.
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductColor productColor;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = LAZY)
    private Order order;

    public Product(String name, int price, int stockQuantity, Category category, ProductColor productColor) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.productColor = productColor;
    }

    public void updateProduct(ProductDto productDto) {
        this.name = productDto.getName();
        this.price = productDto.getPrice();
        this.stockQuantity = productDto.getStockQuantity();
        this.category = productDto.getCategory();
        this.productColor = productDto.getProductColor();
    }
}
