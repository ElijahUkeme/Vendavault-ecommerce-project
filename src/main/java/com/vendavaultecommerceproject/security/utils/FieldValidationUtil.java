package com.vendavaultecommerceproject.security.utils;

import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.security.dto.RegistrationRequest;

import java.util.Objects;

public class FieldValidationUtil {

    public static boolean validateRegistrationFields(RegistrationRequest request) throws DataNotAcceptableException {
        boolean isValid;
        if(Objects.isNull(request.getEmail())|| request.getEmail().isBlank()){
            isValid = false;
            throw new DataNotAcceptableException("Email address is required");

        }
        if(Objects.isNull(request.getLastname())|| request.getLastname().isBlank()){
            isValid = false;
            throw new DataNotAcceptableException("Lastname is required");
        }
        if(Objects.isNull(request.getFirstname())|| request.getFirstname().isBlank()){
            isValid = false;
            throw new DataNotAcceptableException("Firstname is required");
        }
        if(Objects.isNull(request.getPassword())|| request.getPassword().isBlank()){
            isValid = false;
            throw new DataNotAcceptableException("Password is required");
        }
        if(request.getPassword().length()<6){
            isValid = false;
            throw new DataNotAcceptableException("Password must be at least 6 characters");
        }
        return true;
    }
}
