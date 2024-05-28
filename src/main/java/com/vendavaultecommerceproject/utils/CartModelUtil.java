package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.model.cart.CartModel;

public class CartModelUtil {

    public static CartModel getReturnedCartModel(CartItemEntity cartItem){

        CartModel cartModel = CartModel.builder()
                .id(cartItem.getId())
                .price(cartItem.getPrice())
                .addedDate(cartItem.getAddedDate())
                .quantity(cartItem.getQuantity())
                .buyer(UserModelUtil.getReturnedUserModel(cartItem.getBuyer()))
                .product(ProductModelUtil.getReturnedProductModel(cartItem.getProduct()))
                .totalPrice(cartItem.getTotalPrice())
                .build();
        return  cartModel;
    }
}
