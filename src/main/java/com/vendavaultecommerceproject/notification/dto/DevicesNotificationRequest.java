package com.vendavaultecommerceproject.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevicesNotificationRequest extends NotificationRequest{

    @NotBlank
    private String deviceToken;
}
