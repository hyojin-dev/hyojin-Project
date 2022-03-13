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
public class UserMileage extends Timestamped implements HowToPay {

    @Id
    @GeneratedValue
    @Column(name = "user_mileage_id")
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int money;

    public UserMileage(User user) {
        this.user = user;
        this.money = 0;
    }

    public void depositUserMileage(int mileage) {
        this.money += mileage;
    }

    public Boolean withdrawalUserMileage(int mileage) {
        if (this.money < mileage) {
            return false;
        }
        this.money -= mileage;
        return true;
    }
}
