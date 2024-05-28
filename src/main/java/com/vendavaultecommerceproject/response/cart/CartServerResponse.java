package com.vendavaultecommerceproject.response.cart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartServerResponse {

    private String terminus;
    private String status;
    private CartResponse response;
}
