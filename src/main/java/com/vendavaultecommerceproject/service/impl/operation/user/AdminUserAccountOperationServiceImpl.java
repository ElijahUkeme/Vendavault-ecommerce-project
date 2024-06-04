package com.vendavaultecommerceproject.service.impl.operation.user;

import com.vendavaultecommerceproject.dto.operation.AccountOperationDto;
import com.vendavaultecommerceproject.dto.operation.SuspensionAccountViewDto;
import com.vendavaultecommerceproject.entities.operation.user.UserAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.repository.operation.user.AdminUserAccountSuspensionRepository;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import com.vendavaultecommerceproject.response.user.ApiResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.operation.user.AdminUserAccountOperationService;
import com.vendavaultecommerceproject.util.enums.AccountStatus;
import com.vendavaultecommerceproject.utils.UserModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AdminUserAccountOperationServiceImpl implements AdminUserAccountOperationService {

    @Value("${baseUrl}")
    private String baseUrl;

    private final AdminUserAccountSuspensionRepository accountSuspensionRepository;
    private final UserRepository userRepository;

    @Override
    public ServerResponse suspendAccount(AccountOperationDto accountOperationDto, HttpServletRequest request) {

        UserEntity user = userRepository.findByEmail(accountOperationDto.getEmail());
        if (Objects.isNull(user)) {
            return new ServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ApiResponse(
                    406, "User Authentication", "There is no user with the provided email address", null
            ));
        }
        UserAccountSuspensionEntity userAccountSuspensionEntity = UserAccountSuspensionEntity.builder()
                .date(new Date())
                .reason(accountOperationDto.getReason())
                .user(user)
                .build();
        accountSuspensionRepository.save(userAccountSuspensionEntity);
        user.setAccountStatus(AccountStatus.Suspended.name());
        userRepository.save(user);
        return new ServerResponse(baseUrl + request.getRequestURI(), "OK", new ApiResponse(
                200, "User Account", "User account Suspended", null
        ));
    }

    @Override
    public ServerResponse cancelAccountSuspension(AccountOperationDto accountOperationDto, HttpServletRequest request) {
        UserEntity user = userRepository.findByEmail(accountOperationDto.getEmail());
        if (Objects.isNull(user)) {
            return new ServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ApiResponse(
                    406, "User Authentication", "There is no user with the provided email address", null
            ));
        }
        user.setAccountStatus(AccountStatus.Active.name());
        userRepository.save(user);
        return new ServerResponse(baseUrl + request.getRequestURI(), "OK", new ApiResponse(
                200, "User Account", "User account Suspension lifted", UserModelUtil.getReturnedUserModel(user)
        ));
    }

    @Override
    public ServerResponse deleteAccount(AccountOperationDto accountOperationDto, HttpServletRequest request) {
        UserEntity user = userRepository.findByEmail(accountOperationDto.getEmail());
        if (Objects.isNull(user)) {
            return new ServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ApiResponse(
                    406, "User Authentication", "There is no user with the provided email address", null
            ));
        }
        userRepository.delete(user);
        return new ServerResponse(baseUrl + request.getRequestURI(), "OK", new ApiResponse(
                200, "User Account", "User account Deleted Successfully", null
        ));
    }

    @Override
    public ResponseEntity<List<UserAccountSuspensionEntity>> getSuspendedUserInfo(SuspensionAccountViewDto accountViewDto) throws DataNotFoundException {
        UserEntity user = userRepository.findByEmail(accountViewDto.getEmail());
        if (Objects.isNull(user)){
            throw new DataNotFoundException("No user with the provided email");
        }
        List<UserAccountSuspensionEntity> suspendedUserInfo = accountSuspensionRepository.findByUser(user);
        return new ResponseEntity<>(suspendedUserInfo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserAccountSuspensionEntity>> getAllSuspendedUsers() {
        return new ResponseEntity<>(accountSuspensionRepository.findAll(),HttpStatus.OK);
    }
}
