package com.vendavaultecommerceproject.response.sale;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleServerResponse {

    private String terminus;
    private String status;
    SaleResponse response;
}
