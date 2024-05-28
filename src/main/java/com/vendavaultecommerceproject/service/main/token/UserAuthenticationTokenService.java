package com.vendavaultecommerceproject.service.main.token;

import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.user.Usermodel;

public interface UserAuthenticationTokenService {

    public void saveUserAuthenticationToken(UserAuthenticationTokenEntity userAuthenticationTokenEntity);
    public UserEntity getUserByToken(String token) throws DataNotFoundException;
    public UserAuthenticationTokenEntity getTokenByUser(UserEntity user);
}
