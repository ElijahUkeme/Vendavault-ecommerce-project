package com.vendavaultecommerceproject.websocket.chat.service.impl;


import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.websocket.chat.dto.message.ChatMessageDto;
import com.vendavaultecommerceproject.websocket.chat.dto.message.GetChatMessageDto;
import com.vendavaultecommerceproject.websocket.chat.dto.room.ChatRoomDto;
import com.vendavaultecommerceproject.websocket.chat.entities.message.ChatMessage;
import com.vendavaultecommerceproject.websocket.chat.repository.ChatMessageRepository;
import com.vendavaultecommerceproject.websocket.chat.service.main.ChatMessageService;
import com.vendavaultecommerceproject.websocket.chat.service.main.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
    @Override
    public ChatMessage saveMessage(ChatMessageDto chatMessageDto) throws DataNotFoundException {
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .senderId(chatMessageDto.getSenderId())
                .recipientId(chatMessageDto.getRecipientId())
                .createNewRoomIfNotExist(true)
                .build();
        String chatId = chatRoomService.getChatRoomId(chatRoomDto)
                .orElseThrow(()->new DataNotFoundException("Chat Id not found"));
        ChatMessage chatMessage = ChatMessage.builder()
                .chatId(chatId)
                .content(chatMessageDto.getContent())
                .senderId(chatMessageDto.getSenderId())
                .recipientId(chatMessageDto.getRecipientId())
                .timestamp(new Date())
                .build();
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public List<ChatMessage> findChatMessages(GetChatMessageDto chatMessageDto) {
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .senderId(chatMessageDto.getSenderId())
                .recipientId(chatMessageDto.getRecipientId())
                .createNewRoomIfNotExist(false)
                .build();
        var chatId = chatRoomService.getChatRoomId(chatRoomDto);
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());

    }
}
