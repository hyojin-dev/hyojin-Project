package com.example.janghj.domain.User;

import com.example.janghj.domain.Address;
import com.example.janghj.domain.Order;
import com.example.janghj.domain.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")// 반드시 값을 가지도록 합니다.
    private Long id;

    private Long kakaoId;

    @Column(nullable = false)
    private String username;

    @JsonIgnore // user 조회했을 때 json에서 안 뜨게 함
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private String profileImgUrl;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> order = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserCash userCash;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserMileage userMileage;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCoupon> userCoupon = new ArrayList<>();

    @Builder
    public User(String username, String password, String email, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = UserRole.USER;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = UserRole.USER;
    }

    public User(Long kakaoId, String username, String password, String email) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = UserRole.USER;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}
