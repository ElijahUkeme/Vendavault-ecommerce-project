package com.vendavaultecommerceproject.service.main.seller;

import com.vendavaultecommerceproject.dto.password.ResetPasswordDto;
import com.vendavaultecommerceproject.dto.seller.SellerDto;
import com.vendavaultecommerceproject.dto.seller.SellerUpdateDto;
import com.vendavaultecommerceproject.dto.user.SignInDto;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;

public interface SellerService {

    SellerServerResponse registerSeller(MultipartFile file, SellerDto sellerDto, HttpServletRequest request) throws Exception;
    ResponseEntity<?> confirmEmail(String confirmationToken) throws DataNotFoundException;
    SellerServerResponse getSellerByEmail(String email,HttpServletRequest request);

    SellerEntity findSellerByEmail(String email);
    //SellerServerResponse adminApproveSellerRegistration(AdminApproveSellerDto adminApproveSellerDto,HttpServletRequest request);
    SellerServerResponse resetPassword(ResetPasswordDto resetPasswordDto,HttpServletRequest request) throws NoSuchAlgorithmException;
    SellerServerResponse signInSeller(SignInDto signInDto, HttpServletRequest request) throws NoSuchAlgorithmException;
    SellerServerResponse updateSellerInfo(SellerUpdateDto sellerUpdateDto,HttpServletRequest request);
}
