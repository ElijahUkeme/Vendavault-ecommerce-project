package com.vendavaultecommerceproject.payment.utils;

import com.vendavaultecommerceproject.payment.entity.user.UserPaymentStack;
import com.vendavaultecommerceproject.payment.model.user.UserPaymentModel;
import com.vendavaultecommerceproject.utils.SaleModelUtil;
import com.vendavaultecommerceproject.utils.UserModelUtil;

public class UserPaymentModelUtils {

    public static UserPaymentModel getReturnedUserPaymentModel(UserPaymentStack userPaymentStack){
        UserPaymentModel userPaymentModel = UserPaymentModel.builder()
                .id(userPaymentStack.getId())
                .paidAt(userPaymentStack.getPaidAt())
                .accessCode(userPaymentStack.getAccessCode())
                .amount(userPaymentStack.getAmount())
                .channel(userPaymentStack.getChannel())
                .createdAt(userPaymentStack.getCreatedAt())
                .currency(userPaymentStack.getCurrency())
                .gatewayResponse(userPaymentStack.getGatewayResponse())
                .ipAddress(userPaymentStack.getIpAddress())
                .order(SaleModelUtil.getReturnedSaleModel(userPaymentStack.getOrder()))
                .reference(userPaymentStack.getReference())
                .status(userPaymentStack.getStatus())
                .user(UserModelUtil.getReturnedUserModel(userPaymentStack.getUser()))
                .build();
        return userPaymentModel;
    }
}
