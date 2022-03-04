package com.example.janghj.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

    private Long userId;
    private String userName;
    private String message;

    private String roomId;
}