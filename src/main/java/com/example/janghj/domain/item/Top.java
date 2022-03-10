package com.example.janghj.domain.item;

import com.example.janghj.web.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@DiscriminatorValue("TOP")
@Entity
@Getter
@Setter
public class Top extends Item {
    private int topSize;

    public Top(ItemDto itemDto) {
        this.setName(itemDto.getName());
        this.setPrice(itemDto.getPrice());
        this.setStockQuantity(itemDto.getStockQuantity());
        this.setCategory(itemDto.getCategory());
        this.setItemColor(itemDto.getItemColor());
        this.topSize = itemDto.getTopSize();
    }

    public void setTopSize(int topSize) {
        this.topSize = topSize;
    }
}
