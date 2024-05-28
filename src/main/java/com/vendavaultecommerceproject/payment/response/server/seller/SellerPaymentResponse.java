package com.vendavaultecommerceproject.payment.response.server.seller;


import com.vendavaultecommerceproject.payment.model.seller.SellerPaymentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerPaymentResponse {

    private int code;
    private String title;
    private String message;
    private List<SellerPaymentModel> data;
}
