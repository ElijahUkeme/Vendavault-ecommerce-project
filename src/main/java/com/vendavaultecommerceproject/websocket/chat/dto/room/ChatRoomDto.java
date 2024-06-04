package com.vendavaultecommerceproject.websocket.chat.dto.room;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomDto {
    private String senderId;
    private String recipientId;
    private boolean createNewRoomIfNotExist;
}
