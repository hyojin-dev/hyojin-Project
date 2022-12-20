package com.example.janghj.web.dto.cartDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCartDto {
    Long UserId;
    Long productId;
    String productName;
    boolean likeIt;
}
