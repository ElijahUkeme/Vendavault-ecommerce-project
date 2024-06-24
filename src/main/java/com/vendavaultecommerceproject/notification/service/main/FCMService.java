package com.vendavaultecommerceproject.notification.service.main;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.vendavaultecommerceproject.notification.dto.AllDevicesNotificationRequest;
import com.vendavaultecommerceproject.notification.dto.DevicesNotificationRequest;
import com.vendavaultecommerceproject.notification.dto.NotificationSubscriptionRequest;
import com.vendavaultecommerceproject.notification.dto.TopicNotificationRequest;

import java.util.concurrent.ExecutionException;

public interface FCMService {

    public void sendNotificationToDevice(DevicesNotificationRequest request) throws ExecutionException, InterruptedException;

    public void sendPushNotificationToTopic(TopicNotificationRequest request) throws ExecutionException, InterruptedException;
    public void sendMultiCastNotification(AllDevicesNotificationRequest request) throws FirebaseMessagingException;
    public void subscribeDeviceToTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException;
    public void unSubscribeDeviceFromTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException;
}
