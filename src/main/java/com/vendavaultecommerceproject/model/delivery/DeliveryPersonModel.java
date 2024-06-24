package com.vendavaultecommerceproject.model.delivery;


import com.vendavaultecommerceproject.model.seller.SellerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeliveryPersonModel {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String vehicleNumber;
    private String vehicleType;
    private SellerModel sellerModel;
}
