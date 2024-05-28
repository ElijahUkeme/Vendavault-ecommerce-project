package com.vendavaultecommerceproject.response.cart;


import com.vendavaultecommerceproject.model.cart.CartModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListResponse {

    private int code;
    private String title;
    private String message;
    private List<CartModel> data;
}
