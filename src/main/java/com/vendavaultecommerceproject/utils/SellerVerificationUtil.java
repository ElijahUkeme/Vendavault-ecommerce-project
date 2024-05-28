package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class SellerVerificationUtil {

    @Autowired
    private static SellerRepository sellerRepository;
    public static SellerEntity isSellerValid(String email){
        SellerEntity seller = sellerRepository.findByEmail(email);
        if (Objects.nonNull(seller)){
            return seller;
        }
        return  null;
    }
}
