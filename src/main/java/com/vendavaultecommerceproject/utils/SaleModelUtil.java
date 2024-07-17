package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.model.cart.CartModel;
import com.vendavaultecommerceproject.model.sales.SaleModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleModelUtil {

    public static SaleModel getReturnedSaleModel(SaleEntity sale){

        List<CartModel> cartModels = new ArrayList<>();
        List<CartItemEntity> cartItemEntities = sale.getCartItemList();
        for (CartItemEntity cartItem: cartItemEntities){
            cartModels.add(CartModelUtil.getReturnedCartModel(cartItem));
        }
        SaleModel saleModel = SaleModel.builder()
                .id(sale.getId())
                .buyer(UserModelUtil.getReturnedUserModel(sale.getBuyer()))
                .datePurchased(sale.getDatePurchased())
                .paymentType(sale.getPaymentType())
                .cartModels(cartModels)
                .datePurchased(sale.getDatePurchased())
                .timePurchased(sale.getTimePurchased())
                .deliveredPersonName(sale.getDeliveredPersonName())
                .status(sale.getStatus())
                .totalPrice(BigDecimal.valueOf(sale.getTotalPrice()))
                .build();


        return saleModel;
    }
}
