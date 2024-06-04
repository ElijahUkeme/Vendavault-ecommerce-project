package com.vendavaultecommerceproject.websocket.chat.dto.message;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetChatMessageDto {

    private String senderId;
    private String recipientId;
}
