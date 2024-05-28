package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.user.Usermodel;

public class UserModelUtil{

    public static Usermodel getReturnedUserModel(UserEntity user){

        Usermodel usermodel = new Usermodel();
        usermodel.setId(user.getId());
        usermodel.setUserVerified(user.isVerified());
        usermodel.setUsername(user.getUsername());
        usermodel.setEmail(user.getEmail());
        usermodel.setName(user.getName());
        usermodel.setIdentificationUrl(user.getIdentificationUrl());
        usermodel.setPhoneNumber(user.getPhoneNumber());

        return  usermodel;

    }
}
