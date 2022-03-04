package com.example.janghj.domain.User;

import javax.persistence.*;

@Entity
public class UserCoupon {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    // 반드시 값을 가지도록 합니다.
    @Column(name = "user_coupon_id")
    private Long id;

    private String couponName;
    private int RATE_DISCOUNT;
}
