package com.vendavaultecommerceproject.websocket.chat.service.main;

import com.vendavaultecommerceproject.websocket.chat.dto.room.ChatRoomDto;

import java.util.Optional;

public interface ChatRoomService {

    public Optional<String> getChatRoomId(ChatRoomDto chatRoomDto);
}
