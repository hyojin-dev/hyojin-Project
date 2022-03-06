package com.example.janghj.domain.item;

import com.example.janghj.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("SHOES")
@Entity
@Getter
@Setter
public class Shoes extends Item {
    private int shoesSize;
}
