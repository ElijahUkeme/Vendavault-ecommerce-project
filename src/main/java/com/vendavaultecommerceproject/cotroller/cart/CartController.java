package com.vendavaultecommerceproject.cotroller.cart;

import com.vendavaultecommerceproject.dto.cart.AddToCartDto;
import com.vendavaultecommerceproject.dto.cart.BuyerCartItemDto;
import com.vendavaultecommerceproject.dto.cart.UpdateQuantityDto;
import com.vendavaultecommerceproject.response.cart.CartListServerResponse;
import com.vendavaultecommerceproject.response.cart.CartServerResponse;
import com.vendavaultecommerceproject.service.main.cart.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/product/addToCart")
    public CartServerResponse addToCart(@RequestBody AddToCartDto addToCartDto, HttpServletRequest request){
        return cartService.addProductToCart(addToCartDto,request);
    }

    @PostMapping("/cartItem/quantity/update")
    public CartServerResponse updateCartQuantity(@RequestBody UpdateQuantityDto updateQuantityDto,HttpServletRequest request){
        return cartService.updateQuantity(updateQuantityDto,request);
    }

    @DeleteMapping("/cartItem/delete")
    public CartServerResponse deleteCartItem(@PathVariable("itemId")Long itemId,HttpServletRequest request){
        return cartService.deleteCartItem(itemId,request);
    }


    @PostMapping("/cartItems/forBuyer")
    public CartListServerResponse allItemsInTheCartForTheUser(@RequestBody BuyerCartItemDto buyerCartItemDto,HttpServletRequest request){
        return cartService.getAllItemsInTheCartForBuyer(buyerCartItemDto,request);
    }
}
