package com.vendavaultecommerceproject.dto.seller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SellerUpdateDto {

    private String email;
    private String name;
    private String username;
    private String businessName;
    private String phoneNumber;
    private String businessDescription;
}
