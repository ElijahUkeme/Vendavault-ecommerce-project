package com.vendavaultecommerceproject.websocket.chat.service.impl;


import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.websocket.chat.dto.user.UserInfoDto;
import com.vendavaultecommerceproject.websocket.chat.enums.Status;
import com.vendavaultecommerceproject.websocket.chat.repository.UserInfoRepository;
import com.vendavaultecommerceproject.websocket.chat.service.main.UserInfoService;
import com.vendavaultecommerceproject.websocket.chat.entities.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;


    @Override
    public void saveUser(UserInfo userInfo) {
        userInfo.setStatus(Status.Online);
        userInfoRepository.save(userInfo);
        //return new UserInfoCustomResponse("User creation","User created Successfully");
    }

    @Override
    public void disconnect(UserInfo userInfo) throws DataNotFoundException {
        var storedUser = userInfoRepository.findById(userInfo.getUserName())
                .orElseThrow(()->new DataNotFoundException("User not found"));

        if (storedUser !=null){
            storedUser.setStatus(Status.Offline);
            userInfoRepository.save(storedUser);
        }
        //return new UserInfoCustomResponse("Status Info","User logged out Successfully");
    }

    @Override
    public List<UserInfo> findConnectedUser() {

        return userInfoRepository.findAllByStatus(Status.Online);
    }

    @Override
    public List<UserInfo> findAllRegisteredUser() {
        return userInfoRepository.findAll();
    }
}
