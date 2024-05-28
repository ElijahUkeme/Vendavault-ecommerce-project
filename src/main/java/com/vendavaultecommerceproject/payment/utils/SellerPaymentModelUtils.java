package com.vendavaultecommerceproject.payment.utils;

import com.vendavaultecommerceproject.payment.entity.seller.SellerPaymentStack;
import com.vendavaultecommerceproject.payment.model.seller.SellerPaymentModel;
import com.vendavaultecommerceproject.payment.model.user.UserPaymentModel;
import com.vendavaultecommerceproject.utils.ProductModelUtil;
import com.vendavaultecommerceproject.utils.SellerModelUtil;

public class SellerPaymentModelUtils {

    public static SellerPaymentModel getReturnedSellerPaymentUtil(SellerPaymentStack sellerPaymentStack){
        SellerPaymentModel sellerPaymentModel = SellerPaymentModel.builder()
                .id(sellerPaymentStack.getId())
                .paidAt(sellerPaymentStack.getPaidAt())
                .accessCode(sellerPaymentStack.getAccessCode())
                .amount(sellerPaymentStack.getAmount())
                .channel(sellerPaymentStack.getChannel())
                .createdAt(sellerPaymentStack.getCreatedAt())
                .currency(sellerPaymentStack.getCurrency())
                .gatewayResponse(sellerPaymentStack.getGatewayResponse())
                .ipAddress(sellerPaymentStack.getIpAddress())
                .product(ProductModelUtil.getReturnedProductModel(sellerPaymentStack.getProduct()))
                .reference(sellerPaymentStack.getReference())
                .status(sellerPaymentStack.getStatus())
                .seller(SellerModelUtil.getReturnedSeller(sellerPaymentStack.getSeller()))
                .build();
        return sellerPaymentModel;
    }
}
