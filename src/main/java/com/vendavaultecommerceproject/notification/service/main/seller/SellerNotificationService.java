package com.vendavaultecommerceproject.notification.service.main.seller;

import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.model.seller.SellerNotificationModel;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SellerNotificationService {

    public void saveNotification(SaveNotificationDto saveNotificationDto) throws DataNotFoundException;
    public void readNotification(Long notificationId) throws DataNotFoundException;
    public ResponseEntity<List<SellerNotificationModel>> getAllUnreadNotification(RetrieveUserDto userDto) throws DataNotFoundException;
    public ResponseEntity<List<SellerNotificationModel>> getAllSellerNotification(RetrieveUserDto userDto) throws DataNotFoundException;
}
