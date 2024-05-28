package com.vendavaultecommerceproject.service.impl.password;

import com.vendavaultecommerceproject.dto.password.ForgotPasswordDto;
import com.vendavaultecommerceproject.dto.password.RequestOtpDto;
import com.vendavaultecommerceproject.entities.password.ForgotPasswordEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.email.EmailDetails;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import com.vendavaultecommerceproject.repository.password.ForgotPasswordRepository;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import com.vendavaultecommerceproject.response.seller.SellerResponse;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.response.user.ApiResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.email.EmailService;
import com.vendavaultecommerceproject.service.main.password.ForgotPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


@Service
public class ForgotPasswordImpl implements ForgotPasswordService {


    private final ForgotPasswordRepository forgotPasswordRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    ForgotPasswordImpl(ForgotPasswordRepository forgotPasswordRepository, EmailService emailService, UserRepository userRepository, SellerRepository sellerRepository){
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
    }


    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public ServerResponse userForgotPassword(RequestOtpDto requestOtpDto, HttpServletRequest request) {

        UserEntity user = userRepository.findByEmail(requestOtpDto.getEmail());
        Usermodel usermodel = new Usermodel();
        if (Objects.isNull(user)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Forgot Password Information",
                    "The provided email address not found",
                    null));
        }
        if (!user.isVerified()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Forgot Password Information",
                    "The provided email address has not been verified, please verify it before making any request",
                    null));
        }

        usermodel.setId(user.getId());
        usermodel.setUserVerified(user.isVerified());
        usermodel.setUsername(user.getUsername());
        usermodel.setEmail(user.getEmail());
        usermodel.setName(user.getName());
        usermodel.setIdentificationUrl(user.getIdentificationUrl());

        //save the otp to the database
        int otp = generateOtp();
        ForgotPasswordEntity forgotPassword = ForgotPasswordEntity.builder()
                .code(otp)
                .expirationTime(new Date(System.currentTimeMillis()+300*1000))
                .user(user)
                .build();
        forgotPasswordRepository.save(forgotPassword);

        //send an otp to the requested user email

        EmailDetails emailDetails = EmailDetails.builder()
                .subject("PASSWORD RESET")
                .messageBody("Dear "+usermodel.getName()+"\nHere is your otp for password reset\n" +otp+"\n"+
                        "Please note that this" +
                        " otp will expire after 5 minutes\n\nThanks for being one of our valued customer."
                        +"\n\nFrom Vendavault")
                .recipient(user.getEmail())
                .build();
        emailService.sendEmailAlert(emailDetails);

        return new ServerResponse(baseUrl+request.getRequestURI(),"OK",new ApiResponse(200,"Forgot Password Information",
                "An otp has been sent to your email for password modification",
                usermodel));

    }

    private int generateOtp(){
        Random random = new Random();
        return random.nextInt(100_00,999_99);
    }


    @Transactional
    @Modifying
    @Override
    public ServerResponse userVerifyOtp(ForgotPasswordDto forgotPasswordDto, HttpServletRequest request) {


        UserEntity user = userRepository.findByEmail(forgotPasswordDto.getEmail());
        Usermodel usermodel = new Usermodel();
        if (Objects.isNull(user)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Forgot Password Information",
                    "The provided email address not found",
                    null));
        }
        if (!user.isVerified()){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(409,"Forgot Password Information",
                    "The provided email address has not been verified, please verify it before making any request",
                    null));
        }

        ForgotPasswordEntity forgotPassword =forgotPasswordRepository.findByCode(forgotPasswordDto.getCode());
        if (Objects.isNull(forgotPassword)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Otp Verification Information",
                    "The provided otp not found in the database",
                    null));
        }
        if (!(forgotPassword.getUser() ==user)){
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(40,"Otp Verification Information",
                    "The provided otp code does not belongs to this user",
                    null));
        }
        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return new ServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new ApiResponse(406,"Otp Verification Information",
                    "The provided otp code has expired",
                    null));
        }

        usermodel.setId(user.getId());
        usermodel.setUserVerified(user.isVerified());
        usermodel.setUsername(user.getUsername());
        usermodel.setEmail(user.getEmail());
        usermodel.setName(user.getName());
        usermodel.setIdentificationUrl(user.getIdentificationUrl());
        usermodel.setPhoneNumber(user.getPhoneNumber());

        return new ServerResponse(baseUrl+request.getRequestURI(),"OK",new ApiResponse(200,"Otp Verification Information",
                "Otp Verified Successfully",
                null));
    }

    @Override
    public SellerServerResponse sellerForgotPassword(RequestOtpDto requestOtpDto, HttpServletRequest request) {
        SellerEntity seller = sellerRepository.findByEmail(requestOtpDto.getEmail());
        if (Objects.isNull(seller)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Forgot Password Information",
                    "The provided email address not found",
                    null));
        }
        if (!seller.isVerified()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Forgot Password Information",
                    "The provided email address has not been verified",
                    null));
        }
        SellerModel sellerModel = new SellerModel();
        sellerModel.setId(seller.getId());
        sellerModel.setVerified(seller.isVerified());
        sellerModel.setUsername(seller.getUsername());
        sellerModel.setEmail(seller.getEmail());
        sellerModel.setName(seller.getName());
        sellerModel.setIdentificationUrl(seller.getIdentificationUrl());
        sellerModel.setPhoneNumber(seller.getPhoneNumber());
        sellerModel.setBusinessDescription(seller.getBusinessDescription());
        sellerModel.setBusinessName(seller.getBusinessName());

        //save the otp to the database
        int otp = generateOtp();
        ForgotPasswordEntity forgotPassword = ForgotPasswordEntity.builder()
                .code(otp)
                .expirationTime(new Date(System.currentTimeMillis()+300*1000))
                .seller(seller)
                .build();
        forgotPasswordRepository.save(forgotPassword);

        //send an otp to the requested user email

        EmailDetails emailDetails = EmailDetails.builder()
                .subject("PASSWORD RESET")
                .messageBody("Dear "+sellerModel.getName()+"\nHere is your otp for password reset\n" +otp+"\n"+
                        "Please note that this" +
                        " otp will expire after 5 minutes\n\nThanks for being one of our valued customer."
                        +"\n\nFrom Vendavault")
                .recipient(seller.getEmail())
                .build();
        emailService.sendEmailAlert(emailDetails);

        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(200,"Forgot Password Information",
                "An otp has been sent to your email for password modification",
                sellerModel));
    }


    @Transactional
    @Modifying
    @Override
    public SellerServerResponse sellerVerifyOtp(ForgotPasswordDto forgotPasswordDto, HttpServletRequest request) {

        SellerEntity seller = sellerRepository.findByEmail(forgotPasswordDto.getEmail());
        if (Objects.isNull(seller)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Verify otp Information",
                    "The provided email address not found",
                    null));
        }
        if (!seller.isVerified()){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Verify otp Information",
                    "This email address has not been verified",
                    null));
        }

        ForgotPasswordEntity forgotPassword =forgotPasswordRepository.findByCode(forgotPasswordDto.getCode());
        if (Objects.isNull(forgotPassword)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Verify otp Information",
                    "Incorrect otp code",
                    null));
        }
        if (!(forgotPassword.getSeller() ==seller)){
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Verify otp Information",
                    "The provided otp does not belongs to this seller",
                    null));
        }
        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return new SellerServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SellerResponse(406,"Verify otp Information",
                    "The provided otp has expired",
                    null));
        }

        SellerModel sellerModel = new SellerModel();
        sellerModel.setId(seller.getId());
        sellerModel.setVerified(seller.isVerified());
        sellerModel.setUsername(seller.getUsername());
        sellerModel.setEmail(seller.getEmail());
        sellerModel.setName(seller.getName());
        sellerModel.setIdentificationUrl(seller.getIdentificationUrl());
        sellerModel.setPhoneNumber(seller.getPhoneNumber());
        sellerModel.setBusinessDescription(seller.getBusinessDescription());
        sellerModel.setBusinessName(seller.getBusinessName());

        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(200,"Otp Verification Information",
                "Otp Verified Successfully",
                sellerModel));
    }




}
