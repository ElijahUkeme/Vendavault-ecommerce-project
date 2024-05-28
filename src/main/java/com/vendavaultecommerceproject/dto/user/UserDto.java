package com.vendavaultecommerceproject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String name;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
}
