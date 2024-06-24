package com.vendavaultecommerceproject.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class NotificationSubscriptionRequest {

    @NotBlank
    private String deviceToken;
    @NotBlank
    private String topicName;
}
