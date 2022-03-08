package com.example.janghj.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@DiscriminatorValue("OUTER")
@Entity
@Getter
@Setter
public class Outer extends Item {
    private int outerSize;
}
