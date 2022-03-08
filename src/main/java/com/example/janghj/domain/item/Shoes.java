package com.example.janghj.domain.item;

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
}
