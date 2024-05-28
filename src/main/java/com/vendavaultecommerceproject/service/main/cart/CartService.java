package com.vendavaultecommerceproject.service.main.cart;

import com.vendavaultecommerceproject.dto.cart.AddToCartDto;
import com.vendavaultecommerceproject.dto.cart.BuyerCartItemDto;
import com.vendavaultecommerceproject.dto.cart.UpdateQuantityDto;
import com.vendavaultecommerceproject.response.cart.CartListServerResponse;
import com.vendavaultecommerceproject.response.cart.CartServerResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    CartServerResponse addProductToCart(AddToCartDto addToCartDto, HttpServletRequest request);
    CartServerResponse updateQuantity(UpdateQuantityDto updateQuantityDto,HttpServletRequest request);
    CartServerResponse deleteCartItem(Long itemId,HttpServletRequest request);
    CartListServerResponse getAllItemsInTheCartForBuyer(BuyerCartItemDto buyerCartItemDto,HttpServletRequest request);
}
