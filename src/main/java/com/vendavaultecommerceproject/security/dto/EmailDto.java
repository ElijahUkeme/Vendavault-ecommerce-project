package com.vendavaultecommerceproject.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    private String to;
    private String username;
    private String subject;
    private String confirmationUrl;
    private String activationCode;
    private String templateName = "confirm-email";
}
