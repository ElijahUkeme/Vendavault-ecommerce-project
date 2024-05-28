package com.vendavaultecommerceproject.dto.seller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminApproveSellerDto {

    private String status;
    private String sellerEmail;
}
