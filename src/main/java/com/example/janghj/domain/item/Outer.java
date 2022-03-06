package com.example.janghj.domain.item;

import com.example.janghj.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("OUTER")
@Entity
@Getter
@Setter
public class Outer extends Item {
    private int outerSize;
}
