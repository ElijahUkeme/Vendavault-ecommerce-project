package com.vendavaultecommerceproject.response.cart;


import com.vendavaultecommerceproject.model.cart.CartModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private int code;
    private String title;
    private String message;
    private CartModel data;
}
