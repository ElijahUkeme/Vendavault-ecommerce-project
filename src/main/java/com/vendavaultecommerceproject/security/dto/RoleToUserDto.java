package com.vendavaultecommerceproject.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleToUserDto {
    private String userEmail;
    private String roleName;
}
