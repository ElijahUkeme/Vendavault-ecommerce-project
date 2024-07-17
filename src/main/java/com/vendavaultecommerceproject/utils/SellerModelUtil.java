package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.model.seller.SellerModel;

public class SellerModelUtil {

    public static SellerModel getReturnedSeller(SellerEntity seller){
        SellerModel sellerModel = new SellerModel();
        sellerModel.setId(seller.getId());
        sellerModel.setName(seller.getName());
        sellerModel.setEmail(seller.getEmail());
        sellerModel.setUsername(seller.getUsername());
        sellerModel.setBusinessName(seller.getBusinessName());
        sellerModel.setIdentificationUrl(seller.getIdentificationUrl());
        sellerModel.setCreatedDate(seller.getCreatedDate());
        sellerModel.setPhoneNumber(seller.getPhoneNumber());
        sellerModel.setBusinessDescription(seller.getBusinessDescription());
        sellerModel.setVerified(seller.isVerified());

        return sellerModel;
    }
}
