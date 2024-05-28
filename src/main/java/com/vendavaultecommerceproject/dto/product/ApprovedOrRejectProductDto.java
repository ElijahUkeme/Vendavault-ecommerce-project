package com.vendavaultecommerceproject.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedOrRejectProductDto {

    private Long productId;
    private String status;
}
