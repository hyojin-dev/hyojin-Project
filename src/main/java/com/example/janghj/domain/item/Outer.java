package com.example.janghj.domain.item;

import com.example.janghj.web.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@DiscriminatorValue("OUTER")
@Entity
@Getter
@Setter
public class Outer extends Item {
    private int outerSize;

    public Outer(ItemDto itemDto) {
        this.setName(itemDto.getName());
        this.setPrice(itemDto.getPrice());
        this.setStockQuantity(itemDto.getStockQuantity());
        this.setItemColor(itemDto.getItemColor());
        this.outerSize = itemDto.getOuterSize();
    }

    public void setOuterSize(int outerSize) {
        this.outerSize = outerSize;
    }
}
