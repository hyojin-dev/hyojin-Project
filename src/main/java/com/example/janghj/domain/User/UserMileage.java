package com.example.janghj.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class UserMileage {

    @Id
    @GeneratedValue
    @Column(name = "user_mileage_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "userMileage", fetch = LAZY)
    private User user;
}
