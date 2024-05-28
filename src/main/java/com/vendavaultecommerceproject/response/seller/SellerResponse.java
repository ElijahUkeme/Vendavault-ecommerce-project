package com.vendavaultecommerceproject.response.seller;

import com.vendavaultecommerceproject.model.seller.SellerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SellerResponse {

    private int code;
    private String title;
    private String message;
    private SellerModel data;
}
