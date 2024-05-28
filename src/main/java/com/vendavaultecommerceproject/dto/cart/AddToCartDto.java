package com.vendavaultecommerceproject.dto.cart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {

    private Long productId;
    private int quantity;
    private String buyerEmail;
}
