package com.vendavaultecommerceproject.dto.cart;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateQuantityDto {

    private int quantity;
    private Long cartId;
}
