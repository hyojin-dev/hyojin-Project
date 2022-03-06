package com.example.janghj.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("TOP")
@Entity
@Getter
@Setter
public class Top extends Item {
    private int topSize;
}
