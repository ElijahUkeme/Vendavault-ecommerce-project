package com.vendavaultecommerceproject.websocket.chat.controller;


import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.websocket.chat.dto.message.ChatMessageDto;
import com.vendavaultecommerceproject.websocket.chat.dto.message.GetChatMessageDto;
import com.vendavaultecommerceproject.websocket.chat.entities.message.ChatMessage;
import com.vendavaultecommerceproject.websocket.chat.notification.ChatNotification;
import com.vendavaultecommerceproject.websocket.chat.service.main.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessageDto) throws DataNotFoundException {
        ChatMessage chatMessage = chatMessageService.saveMessage(chatMessageDto);
        messagingTemplate.convertAndSendToUser(chatMessage.getRecipientId(),
                "/queue/messages",
                ChatNotification.builder()
                        .chatId(chatMessage.getChatId())
                        .senderId(chatMessage.getSenderId())
                        .recipientId(chatMessage.getRecipientId())
                        .content(chatMessage.getContent())
                        .build());
    }

    @PostMapping("/all/chats/for/user")
    public ResponseEntity<List<ChatMessage>> findAllChats(@RequestBody GetChatMessageDto chatMessageDto){
        return new ResponseEntity<>(chatMessageService.findChatMessages(chatMessageDto), HttpStatus.OK);
    }
}
