package com.vendavaultecommerceproject.dto.sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDto {
    private String paymentType;
    private String buyerEmail;
    private String sellerEmail;
    private String deliveredPersonName;
    private String deliveredAddress;
    private String deliveredPhone;
}
