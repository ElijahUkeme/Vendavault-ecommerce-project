package com.vendavaultecommerceproject.response.product;

import com.vendavaultecommerceproject.model.product.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private int code;
    private String title;
    private String message;
    ProductModel product;
}
