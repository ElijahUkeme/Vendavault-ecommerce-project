package com.vendavaultecommerceproject.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserInfoDto {

    private String email;
    private String name;
    private String username;
    private String phoneNumber;
}
