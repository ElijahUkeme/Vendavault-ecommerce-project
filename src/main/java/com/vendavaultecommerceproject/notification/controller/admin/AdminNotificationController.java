package com.vendavaultecommerceproject.notification.controller.admin;


import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.entity.admin.AdminNotificationEntity;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.service.main.admin.AdminNotificationService;
import com.vendavaultecommerceproject.notification.service.main.user.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/notification")
public class AdminNotificationController {

    private final AdminNotificationService adminNotificationService;

    @PostMapping("/read")
    public void readNotification(@PathVariable("notificationId")Long notificationId) throws DataNotFoundException {
        adminNotificationService.readNotification(notificationId);
    }

    @PostMapping("/all-unread")
    public ResponseEntity<List<AdminNotificationEntity>> getAllUnreadNotification(@RequestBody RetrieveUserDto userDto) throws DataNotFoundException {
        return adminNotificationService.getAllUnreadNotification(userDto);
    }

    @PostMapping("/all")
    public ResponseEntity<List<AdminNotificationEntity>> getAllAdminNotification(@RequestBody RetrieveUserDto userDto) throws DataNotFoundException {
        return adminNotificationService.getAllAdminNotification(userDto);
    }
}
