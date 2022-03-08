package com.example.janghj.domain.item;

import com.example.janghj.domain.Category;
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
    private Category category;

    public Bag(Category category) {
        this.category = category;
    }
}
