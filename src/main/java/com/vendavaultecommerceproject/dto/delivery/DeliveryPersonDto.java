package com.vendavaultecommerceproject.dto.delivery;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPersonDto {
    private String name;
    private String address;
    private String phoneNumber;
    private String vehicleNumber;
    private String vehicleType;
    private String sellerEmail;
}
