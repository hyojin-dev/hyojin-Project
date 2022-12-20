package com.example.janghj.web.dto.cartDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCartDto {
    Long userId;
    String userName;
    Long productId;
    String productName;
    String productPrice;
}
