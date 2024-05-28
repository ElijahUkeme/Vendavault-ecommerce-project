package com.vendavaultecommerceproject.model.seller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerModel {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String businessName;
    private boolean isVerified;
    private String phoneNumber;
    private String businessDescription;
    private String identificationUrl;
}
