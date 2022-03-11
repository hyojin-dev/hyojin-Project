package com.example.janghj.domain.Product;

import com.example.janghj.web.dto.ProductDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PANTS")
public class Pants extends Product {
    private int pantsSize;

    public Pants(ProductDto productDto) {
        super(productDto.getName(), productDto.getPrice(), productDto.getStockQuantity(), productDto.getCategory(), productDto.getProductColor());
        this.pantsSize = productDto.getPantsSize();
    }
}
