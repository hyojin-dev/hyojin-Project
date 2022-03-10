package com.example.janghj.domain.item;

import com.example.janghj.web.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@DiscriminatorValue("BAG") // 자식 테이블을 구분할 구분자 컬럼이름을 지어준다.
@Entity
@Getter
@Setter
public class Bag extends Item {
    private int bagSize;

    public Bag(ItemDto itemDto) {
        this.setName(itemDto.getName());
        this.setPrice(itemDto.getPrice());
        this.setStockQuantity(itemDto.getStockQuantity());
        this.setItemColor(itemDto.getItemColor());
        this.bagSize = itemDto.getBagSize();
    }

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }
}
