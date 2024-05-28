package com.vendavaultecommerceproject.payment.service.user;

import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.user.UserServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserPayStackPaymentService {

    ResponseEntity<CustomPaymentResponse> initializePayment(Long orderId);
    ResponseEntity<CustomPaymentResponse> verifyPayment(Long orderId);
    UserServerResponse getAllPayments(HttpServletRequest request);
    UserServerResponse getAllPaymentsForUser(HttpServletRequest request,String userEmail);

}
