package com.vendavaultecommerceproject.cotroller.password;


import com.vendavaultecommerceproject.dto.password.ForgotPasswordDto;
import com.vendavaultecommerceproject.dto.password.RequestOtpDto;
import com.vendavaultecommerceproject.dto.password.ResetPasswordDto;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.password.ForgotPasswordService;
import com.vendavaultecommerceproject.service.main.seller.SellerService;
import com.vendavaultecommerceproject.service.main.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class ForgotPasswordController {

    private ForgotPasswordService forgotPasswordService;
    private UserService userService;
    private SellerService service;

    ForgotPasswordController(ForgotPasswordService forgotPasswordService, UserService userService, SellerService service){
        this.forgotPasswordService = forgotPasswordService;
        this.userService = userService;
        this.service = service;
    }


    @PostMapping("/user/otp/request")
    public ServerResponse userRequestOtp(@RequestBody RequestOtpDto requestOtpDto, HttpServletRequest request){
        return forgotPasswordService.userForgotPassword(requestOtpDto,request);
    }


    @PostMapping("/user/otp/verify")
    public ServerResponse userVerifyOtp(@RequestBody ForgotPasswordDto forgotPasswordDto,HttpServletRequest request){
        return forgotPasswordService.userVerifyOtp(forgotPasswordDto,request);
    }

    @PostMapping("/user/password/reset")
    public ServerResponse resetPassword(@RequestBody ResetPasswordDto resetPasswordDto,HttpServletRequest request) throws NoSuchAlgorithmException {
        return userService.resetPassword(resetPasswordDto,request);
    }

    @PostMapping("/seller/password/reset")
    public SellerServerResponse resetSellerPassword(@RequestBody ResetPasswordDto resetPasswordDto,HttpServletRequest request) throws NoSuchAlgorithmException {
        return service.resetPassword(resetPasswordDto,request);
    }

    @PostMapping("/seller/otp/request")
    public SellerServerResponse sellerVerifyOtp(@RequestBody RequestOtpDto requestOtpDto,HttpServletRequest request){
        return forgotPasswordService.sellerForgotPassword(requestOtpDto,request);
    }

    @PostMapping("/seller/otp/verify")
    public SellerServerResponse sellerOtpVerify(@RequestBody ForgotPasswordDto forgotPasswordDto,HttpServletRequest request){
        return forgotPasswordService.sellerVerifyOtp(forgotPasswordDto,request);
    }
}
