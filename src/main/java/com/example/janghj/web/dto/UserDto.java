package com.example.janghj.web.dto;

import com.example.janghj.domain.Address;
import lombok.Getter;

@Getter
public class UserDto {
    String username;
    String password;
    String email;
    Address address;

    private boolean admin = false;
    private String adminToken = "";
}
