package com.vendavaultecommerceproject.security.service.main.user;

import com.vendavaultecommerceproject.exception.exeception.DataAlreadyExistException;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.security.dto.AuthenticationRequest;
import com.vendavaultecommerceproject.security.dto.RegistrationRequest;
import com.vendavaultecommerceproject.security.response.AuthenticationResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    public String saveUser(RegistrationRequest request) throws MessagingException, DataNotAcceptableException, DataAlreadyExistException, DataNotFoundException;
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    public String activateAccount(String token) throws DataNotFoundException, DataNotAcceptableException, MessagingException;
}
