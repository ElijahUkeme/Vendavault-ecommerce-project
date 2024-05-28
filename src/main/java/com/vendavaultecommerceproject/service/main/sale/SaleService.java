package com.vendavaultecommerceproject.service.main.sale;

import com.vendavaultecommerceproject.dto.sale.SaleDto;
import com.vendavaultecommerceproject.dto.sale.UpdateSaleDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.sale.SaleListServerResponse;
import com.vendavaultecommerceproject.response.sale.SaleServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface SaleService {
    ResponseEntity<CustomPaymentResponse> saleProduct(SaleDto saleDto);
    SaleServerResponse confirmOrder(UpdateSaleDto updateSaleDto,HttpServletRequest request) throws DataNotFoundException;
    SaleListServerResponse getSaleForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request);
    SaleListServerResponse getAllSales(HttpServletRequest request);

}
