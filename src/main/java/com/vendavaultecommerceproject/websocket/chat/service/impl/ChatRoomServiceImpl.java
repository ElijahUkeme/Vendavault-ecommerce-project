package com.vendavaultecommerceproject.websocket.chat.service.impl;


import com.vendavaultecommerceproject.websocket.chat.dto.room.ChatRoomDto;
import com.vendavaultecommerceproject.websocket.chat.entities.room.ChatRoom;
import com.vendavaultecommerceproject.websocket.chat.repository.ChatRoomRepository;
import com.vendavaultecommerceproject.websocket.chat.service.main.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Optional<String> getChatRoomId(ChatRoomDto chatRoomDto) {
        return chatRoomRepository.findBySenderIdAndRecipientId(chatRoomDto.getSenderId(), chatRoomDto.getRecipientId())
                .map(ChatRoom::getChatId)
                .or(()->{
                    if (chatRoomDto.isCreateNewRoomIfNotExist()){
                        var chatId = createChatId(chatRoomDto.getSenderId(), chatRoomDto.getRecipientId());
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }
    private String createChatId(String senderId,String recipientId){
        String chatId = "";
        try {

            chatId = String.format("%s_%s",senderId,recipientId);
            ChatRoom senderRecipient = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();

            ChatRoom recipientSender = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(recipientId)
                    .recipientId(senderId)
                    .build();

            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);

        }catch (Exception e){
            System.out.println("The stack returns "+e.getMessage());
        }

        return chatId;
    }
}
