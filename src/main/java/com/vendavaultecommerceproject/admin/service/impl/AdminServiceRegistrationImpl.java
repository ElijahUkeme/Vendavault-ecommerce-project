package com.vendavaultecommerceproject.admin.service.impl;


import com.vendavaultecommerceproject.admin.dto.AdminLoginDto;
import com.vendavaultecommerceproject.admin.dto.AdminRegistrationDto;
import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import com.vendavaultecommerceproject.admin.repository.AdminRegistrationRepository;
import com.vendavaultecommerceproject.admin.service.main.AdminRegistrationService;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceRegistrationImpl implements AdminRegistrationService {
    private final AdminRegistrationRepository adminRegistrationRepository;

    @Override
    public ResponseEntity<String> registerAdmin(AdminRegistrationDto registrationDto) throws DataNotAcceptableException {
        if (Objects.isNull(registrationDto.getName())){
            throw new DataNotAcceptableException("Please name is compulsory");
        }
        if (Objects.isNull(registrationDto.getEmail())){
            throw new DataNotAcceptableException("Please provide your email address");
        }
        if (Objects.isNull(registrationDto.getPassword())){
            throw new DataNotAcceptableException("Password is required");
        }
        if (Objects.isNull(registrationDto.getConfirmPassword())){
            throw new DataNotAcceptableException("Confirm password is required");
        }
        if (!(registrationDto.getPassword().equalsIgnoreCase(registrationDto.getConfirmPassword()))){
            throw new DataNotAcceptableException("Confirm password and password are not the same");
        }
        String encryptedCustomerPassword = registrationDto.getPassword();
        try {
            encryptedCustomerPassword = Utility.hashPassword(registrationDto.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdminRegistrationEntity adminRegistrationEntity = AdminRegistrationEntity.builder()
                .name(registrationDto.getName())
                .email(registrationDto.getEmail())
                .password(encryptedCustomerPassword)
                .build();
        adminRegistrationRepository.save(adminRegistrationEntity);
        return new ResponseEntity<>("Admin Registration Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdminRegistrationEntity> loginAdmin(AdminLoginDto adminLoginDto) throws DataNotAcceptableException, DataNotFoundException, NoSuchAlgorithmException {
        if (Objects.isNull(adminLoginDto.getEmail())){
            throw new DataNotAcceptableException("Email address required");
        }
        if (Objects.isNull(adminLoginDto.getPassword())){
            throw new DataNotAcceptableException("Password required");
        }
        AdminRegistrationEntity admin = adminRegistrationRepository.findByEmail(adminLoginDto.getEmail());
        if (Objects.isNull(admin)){
            throw new DataNotFoundException("Incorrect email or password");
        }
        if (!(admin.getPassword().equalsIgnoreCase(Utility.hashPassword(adminLoginDto.getPassword())))){
            throw new DataNotFoundException("Incorrect email or password");
        }
        return new ResponseEntity<>(admin,HttpStatus.OK);

    }

    @Override
    public AdminRegistrationEntity findAdminByEmail(String email) throws DataNotFoundException {
        AdminRegistrationEntity adminRegistrationEntity = adminRegistrationRepository.findByEmail(email);
        if (Objects.isNull(adminRegistrationEntity)){
            throw new DataNotFoundException("Email address not found");
        }
        return adminRegistrationEntity;
    }
}
