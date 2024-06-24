package com.vendavaultecommerceproject.notification.service.main.user;

import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.response.NotificationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserNotificationService {

    public void saveNotification(SaveNotificationDto saveNotificationDto) throws DataNotFoundException;
    public void readNotification(Long notificationId) throws DataNotFoundException;
    public ResponseEntity<List<UserNotificationModel>> getAllUnreadNotification(RetrieveUserDto userDto) throws DataNotFoundException;
    public ResponseEntity<List<UserNotificationModel>> getAllUserNotification(RetrieveUserDto userDto) throws DataNotFoundException;
}
