package com.vendavaultecommerceproject.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {

    private Long productId;
    private String productName;
    private String brand;
    private String category;
    private BigDecimal price;
    private String description;
    private int quantity;
}
