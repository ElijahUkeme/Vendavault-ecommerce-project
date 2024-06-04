package com.vendavaultecommerceproject.websocket.chat.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoCustomResponse {
    private String title;
    private String message;
}
