package com.example.janghj.domain.User;

import com.example.janghj.domain.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class UserCoupon extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // 반드시 값을 가지도록 합니다.
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private int RateDiscount;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
