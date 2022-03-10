package com.example.janghj.domain.item;

import com.example.janghj.web.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@DiscriminatorValue("PANTS")
@Entity
@Getter
@Setter
public class Pants extends Item {
    private int pantsSize;

    public Pants(ItemDto itemDto) {
        this.setName(itemDto.getName());
        this.setPrice(itemDto.getPrice());
        this.setStockQuantity(itemDto.getStockQuantity());
        this.setItemColor(itemDto.getItemColor());
        this.pantsSize = itemDto.getPantsSize();
    }

    public void setBagSize(int pantsSize) {
        this.pantsSize = pantsSize;
    }
}
