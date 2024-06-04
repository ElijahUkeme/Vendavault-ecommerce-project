package com.vendavaultecommerceproject.websocket.chat.service.main;

import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.websocket.chat.dto.message.ChatMessageDto;
import com.vendavaultecommerceproject.websocket.chat.dto.message.GetChatMessageDto;
import com.vendavaultecommerceproject.websocket.chat.entities.message.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    public ChatMessage saveMessage(ChatMessageDto chatMessageDto) throws DataNotFoundException;
    public List<ChatMessage> findChatMessages(GetChatMessageDto chatMessageDto);
}
