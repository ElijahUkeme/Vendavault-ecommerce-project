package com.vendavaultecommerceproject.dto.password;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDto {

    private String email;
    private String password;
    private String confirmPassword;
}
