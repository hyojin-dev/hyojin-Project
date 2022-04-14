package com.example.janghj.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    String username;
    String password;
    String email;
    AddressDto addressDto;

    boolean admin = false;
    String adminToken;

    public UserDto(String username, String password, String email, AddressDto addressDto) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.addressDto = addressDto;
    }
}

