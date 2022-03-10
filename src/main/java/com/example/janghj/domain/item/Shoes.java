package com.example.janghj.domain.item;

import com.example.janghj.web.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@DiscriminatorValue("SHOES")
@Entity
@Getter
@Setter
public class Shoes extends Item {
    private int shoesSize;

    public Shoes(ItemDto itemDto) {
        this.setName(itemDto.getName());
        this.setPrice(itemDto.getPrice());
        this.setStockQuantity(itemDto.getStockQuantity());
        this.setItemColor(itemDto.getItemColor());
        this.shoesSize = itemDto.getBagSize();
    }

    public void setBagSize(int shoesSize) {
        this.shoesSize = shoesSize;
    }
}
