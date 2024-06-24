package com.vendavaultecommerceproject.dto.delivery;


import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewDeliveryPersonDto {
    private String sellerEmail;
    private String deliverPersonPhoneNumber;
    private String deliverPersonVehicleNumber;
}
