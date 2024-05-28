package com.vendavaultecommerceproject.response.sale;

import com.vendavaultecommerceproject.model.sales.SaleModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleListResponse {

    private int code;
    private String title;
    private String message;
    private List<SaleModel> data;
}
