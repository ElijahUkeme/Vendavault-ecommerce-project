package com.vendavaultecommerceproject.dto.seller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String businessName;
    private String phoneNumber;
    private String businessDescription;
}
