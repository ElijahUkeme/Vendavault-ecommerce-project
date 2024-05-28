package com.vendavaultecommerceproject.response.sale;


import com.vendavaultecommerceproject.model.sales.SaleModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private int code;
    private String title;
    private String message;
    private SaleModel data;
}
