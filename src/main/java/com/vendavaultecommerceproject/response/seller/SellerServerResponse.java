package com.vendavaultecommerceproject.response.seller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellerServerResponse {

    private String terminus;
    private String status;
    private SellerResponse response;
}
