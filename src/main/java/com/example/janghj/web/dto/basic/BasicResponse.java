package com.example.janghj.web.dto.basic;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private int status;
    private String message;
    private Object object;

    public BasicResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
        this.object = null;
    }
    public static BasicResponse build(final int status, final String responseMessage) {
        return build(status, responseMessage, null);
    }
    public static BasicResponse build (final int status, final String message, final Object object){
        return BasicResponse.builder()
                .status(status)
                .message(message)
                .object(object)
                .build();
    }
}
