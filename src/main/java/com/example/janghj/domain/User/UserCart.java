package com.example.janghj.domain.User;

import com.example.janghj.domain.Product.Product;
import com.example.janghj.domain.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserCart extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_cart_id")// 반드시 값을 가지도록 합니다.
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "like_it", nullable = false)
    private boolean likeIt;

    public UserCart(User user, Product product, boolean likeIt) {
        this.user = user;
        this.product = product;
        this.likeIt = likeIt;
    }

    public boolean setLikeIt() {
        if (this.likeIt == true)
            return this.likeIt = false;
        else
            return this.likeIt = true;
    }
}