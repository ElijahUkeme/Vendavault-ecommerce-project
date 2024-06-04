package com.vendavaultecommerceproject.service.main.operation.user;

import com.vendavaultecommerceproject.dto.operation.AccountOperationDto;
import com.vendavaultecommerceproject.dto.operation.SuspensionAccountViewDto;
import com.vendavaultecommerceproject.entities.operation.seller.SellerAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.operation.user.UserAccountSuspensionEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminUserAccountOperationService {

    ServerResponse suspendAccount(AccountOperationDto accountOperationDto, HttpServletRequest request);
    ServerResponse cancelAccountSuspension(AccountOperationDto accountOperationDto, HttpServletRequest request);
    ServerResponse deleteAccount(AccountOperationDto accountOperationDto,HttpServletRequest request);
    public ResponseEntity<List<UserAccountSuspensionEntity>> getSuspendedUserInfo(SuspensionAccountViewDto accountViewDto) throws DataNotFoundException;
    public ResponseEntity<List<UserAccountSuspensionEntity>> getAllSuspendedUsers();
}
