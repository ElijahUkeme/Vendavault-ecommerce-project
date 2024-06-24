package com.vendavaultecommerceproject.admin.service.main;


import com.vendavaultecommerceproject.admin.dto.AdminLoginDto;
import com.vendavaultecommerceproject.admin.dto.AdminRegistrationDto;
import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

public interface AdminRegistrationService {

    public ResponseEntity<String> registerAdmin(AdminRegistrationDto registrationDto) throws DataNotAcceptableException;
    public ResponseEntity<AdminRegistrationEntity> loginAdmin(AdminLoginDto adminLoginDto) throws DataNotAcceptableException, DataNotFoundException, NoSuchAlgorithmException;
    public AdminRegistrationEntity findAdminByEmail(String email) throws DataNotFoundException;
}
