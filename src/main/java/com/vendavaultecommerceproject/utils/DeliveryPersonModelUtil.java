package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.delivery.DeliveryPersonEntity;
import com.vendavaultecommerceproject.model.delivery.DeliveryPersonModel;

public class DeliveryPersonModelUtil {

    public static DeliveryPersonModel getReturnedDeliveryPerson(DeliveryPersonEntity deliveryPerson){
        DeliveryPersonModel deliveryPersonModel = DeliveryPersonModel.builder()
                .id(deliveryPerson.getId())
                .address(deliveryPerson.getAddress())
                .name(deliveryPerson.getName())
                .phoneNumber(deliveryPerson.getPhoneNumber())
                .vehicleNumber(deliveryPerson.getVehicleNumber())
                .vehicleType(deliveryPerson.getVehicleType())
                .sellerModel(SellerModelUtil.getReturnedSeller(deliveryPerson.getSeller()))
                .build();
        return deliveryPersonModel;
    }
}
