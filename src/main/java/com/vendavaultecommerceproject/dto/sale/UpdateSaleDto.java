package com.vendavaultecommerceproject.dto.sale;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSaleDto {

    private String status;
    private String token;
}
