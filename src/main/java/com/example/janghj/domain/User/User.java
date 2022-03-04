package com.example.janghj.domain.User;

import com.example.janghj.domain.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    // 반드시 값을 가지도록 합니다.
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = true)
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

    @Embedded
    private Address address;

    @Builder
    public User(String username, String password, String email, UserRole role, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.address = address;
    }

    public User(Long kakaoId, String username, String password, String email, UserRole role ) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
