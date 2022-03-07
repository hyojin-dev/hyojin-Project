package com.example.janghj.domain.item;

import com.example.janghj.domain.Category;
import com.example.janghj.domain.DeliveryStatus;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// 부모 테이블을 구분할 구분자 컬럼이름을 지어준다.
@DiscriminatorColumn(name = "DTYPE")
@Getter
@Setter
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
    private ItemColor color;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;


}
