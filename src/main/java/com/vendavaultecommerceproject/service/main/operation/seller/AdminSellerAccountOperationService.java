package com.vendavaultecommerceproject.service.main.operation.seller;

import com.vendavaultecommerceproject.dto.operation.AccountOperationDto;
import com.vendavaultecommerceproject.dto.operation.SuspensionAccountViewDto;
import com.vendavaultecommerceproject.entities.operation.seller.SellerAccountSuspensionEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminSellerAccountOperationService {

    SellerServerResponse suspendAccount(AccountOperationDto accountOperationDto, HttpServletRequest request);
    SellerServerResponse cancelAccountSuspension(AccountOperationDto accountOperationDto, HttpServletRequest request);
    SellerServerResponse deleteAccount(AccountOperationDto accountOperationDto,HttpServletRequest request);
    public ResponseEntity<List<SellerAccountSuspensionEntity>> getSuspendedSellerInfo(SuspensionAccountViewDto accountViewDto) throws DataNotFoundException;
    public ResponseEntity<List<SellerAccountSuspensionEntity>> getAllSuspendedSellers();
}
