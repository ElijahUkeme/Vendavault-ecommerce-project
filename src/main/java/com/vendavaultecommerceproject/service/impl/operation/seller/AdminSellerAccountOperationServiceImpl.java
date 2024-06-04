package com.vendavaultecommerceproject.service.impl.operation.seller;


import com.vendavaultecommerceproject.dto.operation.AccountOperationDto;
import com.vendavaultecommerceproject.dto.operation.SuspensionAccountViewDto;
import com.vendavaultecommerceproject.entities.operation.seller.SellerAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.repository.operation.seller.AdminSellerAccountSuspensionRepository;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.response.seller.SellerResponse;
import com.vendavaultecommerceproject.response.seller.SellerServerResponse;
import com.vendavaultecommerceproject.service.main.operation.seller.AdminSellerAccountOperationService;
import com.vendavaultecommerceproject.util.enums.AccountStatus;
import com.vendavaultecommerceproject.utils.SellerModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminSellerAccountOperationServiceImpl implements AdminSellerAccountOperationService {
    private final AdminSellerAccountSuspensionRepository repository;
    private final SellerRepository sellerRepository;

    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public SellerServerResponse suspendAccount(AccountOperationDto accountOperationDto, HttpServletRequest request) {

        SellerEntity seller = sellerRepository.findByEmail(accountOperationDto.getEmail());
        if (Objects.isNull(seller)) {
            return new SellerServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new SellerResponse(
                    406, "Seller Authentication", "There is no seller with the provided email address", null
            ));
        }
        SellerAccountSuspensionEntity suspensionEntity = SellerAccountSuspensionEntity.builder()
                .date(new Date())
                .reason(accountOperationDto.getReason())
                .seller(seller)
                .build();
        repository.save(suspensionEntity);
        seller.setAccountStatus(AccountStatus.Suspended.name());
        sellerRepository.save(seller);
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Account","Seller account Suspended", SellerModelUtil.getReturnedSeller(seller)
        ));
    }

    @Override
    public SellerServerResponse cancelAccountSuspension(AccountOperationDto accountOperationDto, HttpServletRequest request) {

        SellerEntity seller = sellerRepository.findByEmail(accountOperationDto.getEmail());
        if (Objects.isNull(seller)) {
            return new SellerServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new SellerResponse(
                    406, "Seller Authentication", "There is no seller with the provided email address", null
            ));
        }
        seller.setAccountStatus(AccountStatus.Active.name());
        sellerRepository.save(seller);
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Account","Seller account Suspended",SellerModelUtil.getReturnedSeller(seller)
        ));
    }

    @Override
    public SellerServerResponse deleteAccount(AccountOperationDto accountOperationDto, HttpServletRequest request) {
        SellerEntity seller = sellerRepository.findByEmail(accountOperationDto.getEmail());
        if (Objects.isNull(seller)) {
            return new SellerServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new SellerResponse(
                    406, "Seller Authentication", "There is no seller with the provided email address", null
            ));
        }
        sellerRepository.delete(seller);
        return new SellerServerResponse(baseUrl+request.getRequestURI(),"OK",new SellerResponse(
                200,"Seller Account","Seller account deleted Successfully",null
        ));
    }

    @Override
    public ResponseEntity<List<SellerAccountSuspensionEntity>> getSuspendedSellerInfo(SuspensionAccountViewDto accountViewDto) throws DataNotFoundException {
        SellerEntity seller = sellerRepository.findByEmail(accountViewDto.getEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("No seller found with the email");
        }
        List<SellerAccountSuspensionEntity> suspendedSeller = repository.findBySeller(seller);
        return new ResponseEntity<>(suspendedSeller, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SellerAccountSuspensionEntity>> getAllSuspendedSellers() {
        return null;
    }
}
