package com.vendavaultecommerceproject.payment.service.seller;

import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerServerPaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface SellerPayStackService {

    ResponseEntity<CustomPaymentResponse> initializePayment(Long productId);
    ResponseEntity<CustomPaymentResponse> verifyPayment(Long productId);
    SellerServerPaymentResponse getAllPayment(HttpServletRequest request);
    SellerServerPaymentResponse getAllPaymentForTheSeller(HttpServletRequest request,String sellerEmail);
}
