package com.vendavaultecommerceproject.service.impl.token;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.token.SellerAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.repository.token.SellerAuthenticationTokenRepository;
import com.vendavaultecommerceproject.service.main.token.SellerAuthenticationTokenService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SellerAuthenticationTokenServiceImpl implements SellerAuthenticationTokenService {

    private SellerAuthenticationTokenRepository authenticationTokenRepository;

    public SellerAuthenticationTokenServiceImpl(SellerAuthenticationTokenRepository authenticationTokenRepository) {
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @Override
    public void saveSellerAuthenticationToken(SellerAuthenticationTokenEntity sellerAuthenticationToken) {
        authenticationTokenRepository.save(sellerAuthenticationToken);
    }

    @Override
    public SellerEntity getSellerByToken(String token) throws DataNotFoundException {
        SellerAuthenticationTokenEntity sellerAuthenticationTokenEntity = authenticationTokenRepository.findByToken(token);
        if (Objects.isNull(sellerAuthenticationTokenEntity)){
            throw new DataNotFoundException("Invalid User's token");
        }else {
            return sellerAuthenticationTokenEntity.getSeller();
        }
    }

    @Override
    public SellerAuthenticationTokenEntity getTokenBySeller(SellerEntity seller) {
        return authenticationTokenRepository.findBySeller(seller);
    }
}
