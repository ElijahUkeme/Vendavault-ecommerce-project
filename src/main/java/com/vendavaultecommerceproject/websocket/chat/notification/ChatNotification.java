package com.vendavaultecommerceproject.websocket.chat.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatNotification {

    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;
}
