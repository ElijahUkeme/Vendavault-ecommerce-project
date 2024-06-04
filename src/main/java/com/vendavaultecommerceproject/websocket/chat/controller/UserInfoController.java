package com.vendavaultecommerceproject.websocket.chat.controller;


import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.websocket.chat.dto.user.UserInfoDto;
import com.vendavaultecommerceproject.websocket.chat.service.main.UserInfoService;
import com.vendavaultecommerceproject.websocket.chat.entities.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;


    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserInfo addUser(@Payload UserInfo userInfo){
        userInfoService.saveUser(userInfo);
        return userInfo;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserInfo disconnectUser(@Payload UserInfo userInfo) throws DataNotFoundException {
        userInfoService.disconnect(userInfo);
        return userInfo;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getConnectedUsers(){
        return new ResponseEntity<>(userInfoService.findConnectedUser(), HttpStatus.OK);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserInfo>> getAllRegisteredUsers(){
        return new ResponseEntity<>(userInfoService.findAllRegisteredUser(),HttpStatus.OK);
    }
}
