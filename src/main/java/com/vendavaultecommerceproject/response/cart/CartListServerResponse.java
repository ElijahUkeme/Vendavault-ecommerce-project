package com.vendavaultecommerceproject.response.cart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListServerResponse {

    private String terminus;
    private String status;
    private CartListResponse response;
}
