package com.example.janghj.domain.User;

import com.example.janghj.domain.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class UserCash extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_cash_id")// 반드시 값을 가지도록 합니다.
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "userCash", fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private int money;

    public UserCash() {
    }

//    public UserCash(User user) {
//        this.user = user;
//        this.money = 0;
//    }

    public void depositUserCash(int cash) {
        this.money += cash;
    }

    public void withdrawalUserCash(int amount) {
        if (this.money < amount) {
            throw new ArithmeticException();
        }
        this.money -= amount;
    }

}