package com.example.janghj.domain.item.category;

import com.example.janghj.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

//@DiscriminatorValue("")
@Entity
@Getter
@Setter
public class Shoes extends Item {
}
