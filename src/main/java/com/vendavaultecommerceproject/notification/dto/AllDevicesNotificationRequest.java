package com.vendavaultecommerceproject.notification.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllDevicesNotificationRequest extends NotificationRequest{

    List<String> deviceTokenList = new ArrayList<>();
}
