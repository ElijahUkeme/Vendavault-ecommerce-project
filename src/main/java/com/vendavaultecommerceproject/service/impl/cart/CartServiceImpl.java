package com.vendavaultecommerceproject.service.impl.cart;


import com.vendavaultecommerceproject.dto.cart.AddToCartDto;
import com.vendavaultecommerceproject.dto.cart.BuyerCartItemDto;
import com.vendavaultecommerceproject.dto.cart.UpdateQuantityDto;
import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.cart.CartModel;
import com.vendavaultecommerceproject.repository.cart.CartRepository;
import com.vendavaultecommerceproject.repository.products.product.ProductRepository;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import com.vendavaultecommerceproject.response.cart.CartListResponse;
import com.vendavaultecommerceproject.response.cart.CartListServerResponse;
import com.vendavaultecommerceproject.response.cart.CartResponse;
import com.vendavaultecommerceproject.response.cart.CartServerResponse;
import com.vendavaultecommerceproject.service.main.cart.CartService;
import com.vendavaultecommerceproject.util.constants.ApiConstant;
import com.vendavaultecommerceproject.util.constants.AppStrings;
import com.vendavaultecommerceproject.utils.CartModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Value("${baseUrl}")
    private String baseUrl;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartServerResponse addProductToCart(AddToCartDto addToCartDto, HttpServletRequest request) {
        ProductEntity product = productRepository.findByProductId(addToCartDto.getProductId());
        if (Objects.isNull(product)){
            return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusNotOk,new CartResponse(
                    ApiConstant.STATUS_CODE_NOT_FOUND, AppStrings.productAuthenticationHeadingMessage,AppStrings.productIdNotFoundMessage,null
            ));
        }
        UserEntity buyer = userRepository.findByEmail(addToCartDto.getBuyerEmail());
        if (Objects.isNull(buyer)){
            return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusNotOk,new CartResponse(
                    ApiConstant.STATUS_CODE_NOT_FOUND,AppStrings.buyerAuthenticationHeading,AppStrings.buyerEmailNotFoundMessage,null
            ));
        }
        if (addToCartDto.getQuantity() <1){
            return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusNotOk,new CartResponse(
                    ApiConstant.STATUS_CODE_NOT_FOUND,AppStrings.productAuthenticationHeadingMessage,AppStrings.productLowQuantityMessage,null
            ));
        }
        //first of all check if the user has already added that product to his cart
        //will demand that he/she should rather update the one added already
        //rather than adding it again and again
        if (isUserAlreadyHasThisProductInHisCart(buyer,product)){
            return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusNotOk,new CartResponse(
                    ApiConstant.STATUS_CODE_NOT_ACCEPTED,AppStrings.cartAuthenticationHeadingMessage,AppStrings.productAlreadyAddedToCartMessage,null
            ));
        }
        CartItemEntity cartItem = CartItemEntity.builder()
                .price(product.getPrice().intValue())
                .addedDate(new Date())
                .quantity(addToCartDto.getQuantity())
                .buyer(buyer)
                .seller(product.getProductOwner())
                .totalPrice(product.getPrice().intValue()*addToCartDto.getQuantity())
                .product(product)
                .checkOut(false)
                .build();
        cartRepository.save(cartItem);

        return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusOk,new CartResponse(
                ApiConstant.STATUS_CODE_OK,AppStrings.cartAuthenticationHeadingMessage,AppStrings.productAddedToCartSuccessMessage, CartModelUtil.getReturnedCartModel(cartItem)
        ));
    }

    @Override
    public CartServerResponse updateQuantity(UpdateQuantityDto updateQuantityDto, HttpServletRequest request) {
        Optional<CartItemEntity> cartItem = cartRepository.findById(updateQuantityDto.getCartId());
        if (Objects.isNull(cartItem)){
            return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusNotOk,new CartResponse(
                    ApiConstant.STATUS_CODE_NOT_FOUND,AppStrings.cartAuthenticationHeadingMessage,AppStrings.cartItemIdNotFoundMessage,null
            ));
        }
        cartItem.get().setQuantity(updateQuantityDto.getQuantity());
        cartRepository.save(cartItem.get());
        return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusOk,new CartResponse(
                ApiConstant.STATUS_CODE_OK,AppStrings.cartAuthenticationHeadingMessage,AppStrings.cartItemUpdateMessage, CartModelUtil.getReturnedCartModel(cartItem.get())
        ));
    }

    @Override
    public CartServerResponse deleteCartItem(Long itemId, HttpServletRequest request) {
        Optional<CartItemEntity> cartItem = cartRepository.findById(itemId);
        if (Objects.isNull(cartItem)){
            return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusNotOk,new CartResponse(
                    ApiConstant.STATUS_CODE_NOT_FOUND,AppStrings.cartAuthenticationHeadingMessage,AppStrings.cartItemIdNotFoundMessage,null
            ));
        }
        cartRepository.delete(cartItem.get());
        return new CartServerResponse(baseUrl+request.getRequestURI(),AppStrings.statusOk,new CartResponse(
                ApiConstant.STATUS_CODE_OK,AppStrings.cartItemDeletionHeadingMessage,AppStrings.cartItemDeletionMessage, CartModelUtil.getReturnedCartModel(cartItem.get())
        ));
    }

    @Override
    public CartListServerResponse getAllItemsInTheCartForBuyer(BuyerCartItemDto buyerCartItemDto, HttpServletRequest request) {
        UserEntity buyer = userRepository.findByEmail(buyerCartItemDto.getBuyerEmail());
        if (Objects.isNull(buyer)){
            return new CartListServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new CartListResponse(
                    406,"Buyer Authentication","Email address not found",null
            ));
        }
        List<CartItemEntity> cartItemEntityList = cartRepository.findByBuyer(buyer);
        List<CartModel> cartModels = new ArrayList<>();
        for (CartItemEntity cartItem: cartItemEntityList){
            if (!(cartItem.isCheckOut())){
                cartModels.add(CartModelUtil.getReturnedCartModel(cartItem));
            }
        }
        return new CartListServerResponse(baseUrl+request.getRequestURI(),"OK",
                new CartListResponse(200,"Cart List","Your cart item list",cartModels));
    }

    boolean isUserAlreadyHasThisProductInHisCart(UserEntity buyer,ProductEntity product){
        List<CartItemEntity> cartItemEntityList = cartRepository.findByBuyer(buyer);
        for (CartItemEntity cartItem: cartItemEntityList){
            if (!(cartItem.isCheckOut())){
                return cartItem.getProduct() == product;
            }
        }
        return false;
    }
}
