package com.vendavaultecommerceproject.notification.controller.seller;


import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.model.seller.SellerNotificationModel;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import com.vendavaultecommerceproject.notification.service.main.seller.SellerNotificationService;
import com.vendavaultecommerceproject.notification.service.main.user.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seller/notification")
public class SellerNotificationController {

    private final SellerNotificationService sellerNotificationService;

    @PostMapping("/read")
    public void readNotification(@PathVariable("notificationId")Long notificationId) throws DataNotFoundException {
        sellerNotificationService.readNotification(notificationId);
    }

    @PostMapping("/all-unread")
    public ResponseEntity<List<SellerNotificationModel>> getAllUnreadNotification(@RequestBody RetrieveUserDto userDto) throws DataNotFoundException {
        return sellerNotificationService.getAllUnreadNotification(userDto);
    }

    @PostMapping("/all")
    public ResponseEntity<List<SellerNotificationModel>> getAllSellerNotification(@RequestBody RetrieveUserDto userDto) throws DataNotFoundException {
        return sellerNotificationService.getAllSellerNotification(userDto);
    }
}
