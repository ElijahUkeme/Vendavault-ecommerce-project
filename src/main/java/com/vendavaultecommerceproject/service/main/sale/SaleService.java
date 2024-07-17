package com.vendavaultecommerceproject.service.main.sale;

import com.vendavaultecommerceproject.dto.sale.SaleDto;
import com.vendavaultecommerceproject.dto.sale.UpdateSaleDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.notification.model.NotificationModel;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.sale.SaleListServerResponse;
import com.vendavaultecommerceproject.response.sale.SaleServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SaleService {

    SaleEntity getSaleByOrderId(Long orderId) throws DataNotFoundException;
    ResponseEntity<CustomPaymentResponse> saleProduct(SaleDto saleDto) throws DataNotFoundException, ExecutionException, InterruptedException;
    SaleServerResponse confirmOrder(UpdateSaleDto updateSaleDto,HttpServletRequest request) throws DataNotFoundException;
    //SaleListServerResponse getSaleForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request);
    ResponseEntity<List<NotificationModel>> allOrdersForTheSeller(RetrieveUserDto userDto) throws DataNotFoundException;
    SaleListServerResponse getAllSales(HttpServletRequest request);

}
