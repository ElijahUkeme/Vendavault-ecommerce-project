package com.vendavaultecommerceproject.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductAfterSaleDto {

    private Long productId;
    private int quantity;
}
