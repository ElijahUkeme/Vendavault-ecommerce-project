package com.vendavaultecommerceproject.notification.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveNotificationDto {

    private String title;
    private String message;
    private String email;
}
