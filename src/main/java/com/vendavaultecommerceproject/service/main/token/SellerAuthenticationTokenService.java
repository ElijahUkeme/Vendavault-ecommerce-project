package com.vendavaultecommerceproject.service.main.token;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.token.SellerAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;

public interface SellerAuthenticationTokenService {

    public void saveSellerAuthenticationToken(SellerAuthenticationTokenEntity sellerAuthenticationToken);
    public SellerEntity getSellerByToken(String token) throws DataNotFoundException;
    public SellerAuthenticationTokenEntity getTokenBySeller(SellerEntity seller);
}
