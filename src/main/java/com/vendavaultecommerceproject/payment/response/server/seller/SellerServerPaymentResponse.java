package com.vendavaultecommerceproject.payment.response.server.seller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerServerPaymentResponse {
    private String terminus;
    private String status;
    private SellerPaymentResponse response;
}
