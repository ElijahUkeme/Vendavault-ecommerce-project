package com.vendavaultecommerceproject.response.product;


import com.vendavaultecommerceproject.model.product.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {

    private int code;
    private String title;
    private String message;
    List<ProductModel> products;
}
