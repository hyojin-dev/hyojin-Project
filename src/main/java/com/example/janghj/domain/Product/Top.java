package com.example.janghj.domain.Product;

import com.example.janghj.domain.Category;
import com.example.janghj.web.dto.ProductDto;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TOP")
public class Top extends Product {

    private int topSize;

    public Top(ProductDto productDto) {
        super(productDto.getName(), productDto.getPrice(), productDto.getStockQuantity(), productDto.getCategory(), productDto.getProductColor());
        this.topSize = productDto.getTopSize();
    }
}
