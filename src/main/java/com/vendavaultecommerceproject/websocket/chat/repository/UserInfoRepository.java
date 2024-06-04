package com.vendavaultecommerceproject.websocket.chat.repository;

import com.vendavaultecommerceproject.websocket.chat.entities.user.UserInfo;
import com.vendavaultecommerceproject.websocket.chat.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    List<UserInfo> findAllByStatus(Status status);
}
