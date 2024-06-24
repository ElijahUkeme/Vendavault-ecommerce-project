//package com.vendavaultecommerceproject.notification.controller;
//
//
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.vendavaultecommerceproject.notification.dto.AllDevicesNotificationRequest;
//import com.vendavaultecommerceproject.notification.dto.DevicesNotificationRequest;
//import com.vendavaultecommerceproject.notification.dto.NotificationSubscriptionRequest;
//import com.vendavaultecommerceproject.notification.dto.TopicNotificationRequest;
//import com.vendavaultecommerceproject.notification.service.main.FCMService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.ExecutionException;
//
//@RestController
//@RequiredArgsConstructor
//public class NotificationController {
//
//    private final FCMService fcmService;
//
//    @PostMapping("/notification/send/to/device")
//    public ResponseEntity<String> sendNotification(@RequestBody @Valid DevicesNotificationRequest request) {
//        try {
//            fcmService.sendNotificationToDevice(request);
//            return ResponseEntity.ok("Notification sent successfully.");
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification.");
//        }
//    }
//
//    @PostMapping("/notification/send/to/topic")
//    public ResponseEntity<String> sendNotificationToTopic(@RequestBody @Valid TopicNotificationRequest request) {
//        try {
//            fcmService.sendPushNotificationToTopic(request);
//            return ResponseEntity.ok("Notification sent successfully.");
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification.");
//        }
//    }
//
//    @PostMapping("/notification/send/to/all")
//    public ResponseEntity<String> sendNotificationToAll(@RequestBody @Valid AllDevicesNotificationRequest request) {
//        try {
//            fcmService.sendMultiCastNotification(request);
//            return ResponseEntity.ok("Multicast notification sent successfully.");
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send multicast notification.");
//        }
//    }
//
//    @PostMapping("/notification/subscribe")
//    public ResponseEntity<String> subscribeToTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
//        try {
//            fcmService.subscribeDeviceToTopic(request);
//            return ResponseEntity.ok("Device subscribed to the topic successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to subscribe device to the topic.");
//        }
//    }
//
//    @PostMapping("/notification/unsubscribe")
//    public ResponseEntity<String> unsubscribeFromTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
//        try {
//            fcmService.unSubscribeDeviceFromTopic(request);
//            return ResponseEntity.ok("Device unsubscribed from the topic successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unsubscribe device from the topic.");
//        }
//    }
//}
