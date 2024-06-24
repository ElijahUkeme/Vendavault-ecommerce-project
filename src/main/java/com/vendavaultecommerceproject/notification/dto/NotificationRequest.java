package com.vendavaultecommerceproject.notification.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private String imageUrl;
    private Map<String, String> data = new HashMap<>();
}
