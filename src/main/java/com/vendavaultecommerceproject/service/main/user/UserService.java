package com.vendavaultecommerceproject.service.main.user;

import com.vendavaultecommerceproject.dto.user.UpdateUserInfoDto;
import com.vendavaultecommerceproject.dto.user.UserDto;
import com.vendavaultecommerceproject.dto.password.ResetPasswordDto;
import com.vendavaultecommerceproject.dto.user.SignInDto;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    ServerResponse registerUser(MultipartFile file, UserDto userDto, HttpServletRequest request) throws Exception;
    ResponseEntity<?> confirmEmail(String confirmationToken) throws DataNotFoundException;
    ServerResponse getUserByEmail(String email,HttpServletRequest request);
    UserEntity findUserByEmail(String email);
    ServerResponse resetPassword(ResetPasswordDto resetPasswordDto, HttpServletRequest request) throws NoSuchAlgorithmException;

    ServerResponse signInUser(SignInDto signInDto,HttpServletRequest request) throws NoSuchAlgorithmException;
    ServerResponse updateUserInfo(UpdateUserInfoDto userInfoDto,HttpServletRequest request);
}
