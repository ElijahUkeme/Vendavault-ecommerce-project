package com.vendavaultecommerceproject.websocket.chat.repository;

import com.vendavaultecommerceproject.websocket.chat.entities.message.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,String> {

    List<ChatMessage> findByChatId(String chatId);
}
