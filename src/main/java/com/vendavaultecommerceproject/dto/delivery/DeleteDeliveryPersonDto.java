package com.vendavaultecommerceproject.dto.delivery;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteDeliveryPersonDto {

    private String sellerEmail;
    private String deliveryPersonPhoneNumber;
}
