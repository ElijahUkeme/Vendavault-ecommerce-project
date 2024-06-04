package com.vendavaultecommerceproject.payment.service.video;

import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerServerPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.video.VideoPaymentServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface VideoPaymentService {

    ResponseEntity<CustomPaymentResponse> initializePayment(Long videoId);
    ResponseEntity<CustomPaymentResponse> verifyPayment(Long videoId);
    VideoPaymentServerResponse getAllPayment(HttpServletRequest request);
    VideoPaymentServerResponse getAllPaymentForTheSeller(HttpServletRequest request, String sellerEmail);
}
