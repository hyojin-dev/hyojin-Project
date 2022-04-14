package com.example.janghj.domain.Product;

import com.example.janghj.domain.Category;
import com.example.janghj.web.dto.ProductDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TOP")
public class Top extends Product {

    private int topSize;

    public Top(ProductDto productDto) {
        super(productDto.getName(), productDto.getPrice(), productDto.getStockQuantity(), productDto.getCategory(), productDto.getProductColor());
        this.topSize = productDto.getSize();
    }

    //  test 용도
    public Top(int topSize) {
        super("test", 1234, 12, Category.TOP, ProductColor.BLUE);
        this.topSize = topSize;
    }
}
