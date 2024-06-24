package com.vendavaultecommerceproject.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRegistrationDto {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
