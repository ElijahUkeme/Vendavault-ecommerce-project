package com.vendavaultecommerceproject.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TopicNotificationRequest extends NotificationRequest{

    @NotBlank
    private String topicName;
}
