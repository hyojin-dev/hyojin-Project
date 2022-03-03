package com.example.janghj.domain.item;

import com.example.janghj.domain.Timestamped;

import javax.persistence.*;

public abstract class Item extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Color color;
}
