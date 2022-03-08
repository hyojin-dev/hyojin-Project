package com.example.janghj.domain.item;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.item.Item;
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
    private Category category;
}
