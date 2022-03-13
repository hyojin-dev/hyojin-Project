package com.example.janghj.domain.User;

import com.example.janghj.domain.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCash extends Timestamped implements HowToPay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usercash_id")// 반드시 값을 가지도록 합니다.
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int money;

    public UserCash(User user) {
        this.user = user;
        this.money = 0;
    }

    public void depositUserCash(int cash) {
        this.money += cash;
    }

    public Boolean withdrawalUserCash(int cash) {
        if (this.money < cash) {
            return false;
        }
        this.money -= cash;
        return true;
    }

}
