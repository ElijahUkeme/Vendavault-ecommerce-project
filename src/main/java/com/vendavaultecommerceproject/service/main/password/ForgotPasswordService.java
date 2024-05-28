package com.vendavaultecommerceproject.service.main.password;

import com.vendavaultecommerceproject.dto.password.ForgotPasswordDto;
import com.vendavaultecommerceproject.dto.password.RequestOtpDto;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ForgotPasswordService {

    ServerResponse userForgotPassword(RequestOtpDto requestOtpDto, HttpServletRequest request);
    ServerResponse userVerifyOtp(ForgotPasswordDto forgotPasswordDto,HttpServletRequest request);
    SellerServerResponse sellerForgotPassword(RequestOtpDto requestOtpDto,HttpServletRequest request);
    SellerServerResponse sellerVerifyOtp(ForgotPasswordDto forgotPasswordDto,HttpServletRequest request);
}
