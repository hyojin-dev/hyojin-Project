package com.example.janghj.domain.User;

import com.example.janghj.domain.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserLikes {

    @Id
    @GeneratedValue
    @Column(name = "userLikes_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "item_id")
//    private Product product;

}
