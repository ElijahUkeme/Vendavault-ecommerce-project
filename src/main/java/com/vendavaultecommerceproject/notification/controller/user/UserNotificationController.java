package com.vendavaultecommerceproject.notification.controller.user;


import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.service.main.user.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/notification")
public class UserNotificationController {

    private final UserNotificationService userNotificationService;

    @PostMapping("/read")
    public void readNotification(@PathVariable("notificationId")Long notificationId) throws DataNotFoundException {
        userNotificationService.readNotification(notificationId);
    }

    @PostMapping("/all-unread")
    public ResponseEntity<List<UserNotificationModel>> getAllUnreadNotification(@RequestBody RetrieveUserDto userDto) throws DataNotFoundException {
        return userNotificationService.getAllUnreadNotification(userDto);
    }

    @PostMapping("/all")
    public ResponseEntity<List<UserNotificationModel>> getAllUserNotification(@RequestBody RetrieveUserDto userDto) throws DataNotFoundException {
        return userNotificationService.getAllUserNotification(userDto);
    }
}
