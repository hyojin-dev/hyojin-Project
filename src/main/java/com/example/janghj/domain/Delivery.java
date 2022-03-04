package com.example.janghj.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Delivery {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "USER_ID")
    private Long id;
}
