package com.example.janghj.domain;


import com.example.janghj.web.dto.AddressDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Address(AddressDto addressDto) {
        this.city = addressDto.getCity();
        this.street = addressDto.getStreet();
        this.zipcode = addressDto.getZipcode();
    }
}
