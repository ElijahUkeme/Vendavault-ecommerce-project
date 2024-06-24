package com.vendavaultecommerceproject.notification.service.main.admin;

import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.entity.admin.AdminNotificationEntity;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminNotificationService {

    public void saveNotification(SaveNotificationDto saveNotificationDto) throws DataNotFoundException;
    public void readNotification(Long notificationId) throws DataNotFoundException;
    public ResponseEntity<List<AdminNotificationEntity>> getAllUnreadNotification(RetrieveUserDto userDto) throws DataNotFoundException;
    public ResponseEntity<List<AdminNotificationEntity>> getAllAdminNotification(RetrieveUserDto userDto) throws DataNotFoundException;
}
