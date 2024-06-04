package com.vendavaultecommerceproject.websocket.chat.service.main;

import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.websocket.chat.dto.user.UserInfoDto;
import com.vendavaultecommerceproject.websocket.chat.entities.user.UserInfo;

import java.util.List;

public interface UserInfoService {

    public void saveUser(UserInfo userInfo);
    public void disconnect(UserInfo userInfo) throws DataNotFoundException;
    public List<UserInfo> findConnectedUser();
    public List<UserInfo> findAllRegisteredUser();
}
