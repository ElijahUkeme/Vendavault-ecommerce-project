package com.vendavaultecommerceproject.service.impl.token;

import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.user.Usermodel;
import com.vendavaultecommerceproject.repository.token.UserAuthenticationTokenRepository;
import com.vendavaultecommerceproject.service.main.token.UserAuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserAuthenticationTokenServiceImpl implements UserAuthenticationTokenService {

    @Autowired
    private UserAuthenticationTokenRepository userAuthenticationTokenRepository;
    @Override
    public void saveUserAuthenticationToken(UserAuthenticationTokenEntity userAuthenticationTokenEntity) {
        userAuthenticationTokenRepository.save(userAuthenticationTokenEntity);
    }

    @Override
    public UserEntity getUserByToken(String token) throws DataNotFoundException {
        UserAuthenticationTokenEntity userAuthenticationTokenEntity = userAuthenticationTokenRepository.findByToken(token);
        if (Objects.isNull(userAuthenticationTokenEntity)){
            throw new DataNotFoundException("Invalid User's token");
        }else {
            return userAuthenticationTokenEntity.getUser();
        }
    }

    @Override
    public UserAuthenticationTokenEntity getTokenByUser(UserEntity user) {
        return userAuthenticationTokenRepository.findByUser(user);
    }
}
