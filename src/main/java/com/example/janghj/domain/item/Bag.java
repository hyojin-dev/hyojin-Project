package com.example.janghj.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// 자식 테이블을 구분할 구분자 컬럼이름을 지어준다.
@DiscriminatorValue("BAG")
@Entity
@Getter
@Setter
public class Bag extends Item {
    private int bagSize;
}
