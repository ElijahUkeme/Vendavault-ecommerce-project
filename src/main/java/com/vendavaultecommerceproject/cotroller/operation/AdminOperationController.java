package com.vendavaultecommerceproject.cotroller.operation;


import com.vendavaultecommerceproject.dto.operation.AccountOperationDto;
import com.vendavaultecommerceproject.dto.operation.SuspensionAccountViewDto;
import com.vendavaultecommerceproject.entities.operation.seller.SellerAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.operation.user.UserAccountSuspensionEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.operation.seller.AdminSellerAccountOperationService;
import com.vendavaultecommerceproject.service.main.operation.user.AdminUserAccountOperationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminOperationController {

    private final AdminUserAccountOperationService userAccountOperationService;
    private final AdminSellerAccountOperationService sellerAccountOperationService;


    @PostMapping("/admin/suspend/user/account")
    public ServerResponse suspendUserAccount(@RequestBody AccountOperationDto accountOperationDto, HttpServletRequest request){
        return userAccountOperationService.suspendAccount(accountOperationDto,request);
    }
    @DeleteMapping("/admin/user/account/delete")
    public ServerResponse userAccountDelete(@RequestBody AccountOperationDto accountOperationDto, HttpServletRequest request){
        return userAccountOperationService.deleteAccount(accountOperationDto,request);
    }
    @PostMapping("/admin/user/account/suspension/lifted")
    public ServerResponse userAccountSuspensionLifted(@RequestBody AccountOperationDto accountOperationDto, HttpServletRequest request){
        return userAccountOperationService.cancelAccountSuspension(accountOperationDto,request);
    }

    @PostMapping("/admin/seller/account/suspend")
    public SellerServerResponse suspendAccount(@RequestBody AccountOperationDto accountOperationDto, HttpServletRequest request){
        return sellerAccountOperationService.suspendAccount(accountOperationDto,request);
    }

    @PostMapping("/admin/seller/account/delete")
    public SellerServerResponse deleteAccount(@RequestBody AccountOperationDto accountOperationDto, HttpServletRequest request){
        return sellerAccountOperationService.deleteAccount(accountOperationDto,request);
    }

    @PostMapping("/admin/seller/account/suspension/lifted")
    public SellerServerResponse accountSuspensionLifted(@RequestBody AccountOperationDto accountOperationDto, HttpServletRequest request){
        return sellerAccountOperationService.cancelAccountSuspension(accountOperationDto,request);
    }

    @PostMapping("/admin/suspended/seller")
    public ResponseEntity<List<SellerAccountSuspensionEntity>> getSuspendedSellerAccount(@RequestBody SuspensionAccountViewDto accountViewDto) throws DataNotFoundException {
        return sellerAccountOperationService.getSuspendedSellerInfo(accountViewDto);
    }
    @PostMapping("/admin/all/suspended/sellers")
    public ResponseEntity<List<SellerAccountSuspensionEntity>> getAllSuspendedSellers() {
        return sellerAccountOperationService.getAllSuspendedSellers();
    }

    @PostMapping("/admin/suspended/user")
    public ResponseEntity<List<UserAccountSuspensionEntity>> getSuspendedUserAccount(@RequestBody SuspensionAccountViewDto accountViewDto) throws DataNotFoundException {
        return userAccountOperationService.getSuspendedUserInfo(accountViewDto);
    }
    @PostMapping("/admin/all/suspended/users")
    public ResponseEntity<List<UserAccountSuspensionEntity>> getAllSuspendedUsers() {
        return userAccountOperationService.getAllSuspendedUsers();
    }
}
