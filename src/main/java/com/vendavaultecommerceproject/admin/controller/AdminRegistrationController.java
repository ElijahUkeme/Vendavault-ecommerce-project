package com.vendavaultecommerceproject.admin.controller;


import com.vendavaultecommerceproject.admin.dto.AdminLoginDto;
import com.vendavaultecommerceproject.admin.dto.AdminRegistrationDto;
import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import com.vendavaultecommerceproject.admin.service.main.AdminRegistrationService;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/admin")
public class AdminRegistrationController {

    private final AdminRegistrationService adminRegistrationService;


    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegistrationDto adminRegistrationDto) throws DataNotAcceptableException {
        return adminRegistrationService.registerAdmin(adminRegistrationDto);
    }
    @PostMapping("/login")
    public ResponseEntity<AdminRegistrationEntity> loginAdmin(@RequestBody AdminLoginDto adminLoginDto) throws DataNotFoundException, DataNotAcceptableException, NoSuchAlgorithmException {
        return adminRegistrationService.loginAdmin(adminLoginDto);
    }
}
