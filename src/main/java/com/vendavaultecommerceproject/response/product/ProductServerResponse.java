package com.vendavaultecommerceproject.response.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductServerResponse {

    private String terminus;
    private String status;
    private ProductResponse response;
}
