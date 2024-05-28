package com.vendavaultecommerceproject.response.sale;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleListServerResponse {
    private String terminus;
    private String status;
    private SaleListResponse response;
}
