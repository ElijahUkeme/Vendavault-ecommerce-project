package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class UserVerificationUtil {


    @Autowired
    private static UserRepository repository;

    public static UserEntity isUserValid(String email){
        UserEntity user = repository.findByEmail(email);
        if (Objects.nonNull(user)){
            return user;
        }else {
            return null;
        }
    }
}
