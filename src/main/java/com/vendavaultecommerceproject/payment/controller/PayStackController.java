package com.vendavaultecommerceproject.payment.controller;

import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerServerPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.user.UserServerResponse;
import com.vendavaultecommerceproject.payment.response.server.video.VideoPaymentServerResponse;
import com.vendavaultecommerceproject.payment.service.seller.SellerPayStackService;
import com.vendavaultecommerceproject.payment.service.user.UserPayStackPaymentService;
import com.vendavaultecommerceproject.payment.service.video.VideoPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/payStack",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PayStackController {

    private final UserPayStackPaymentService userPayStackPaymentService;
    private final SellerPayStackService sellerPayStackService;
    private final VideoPaymentService videoPaymentService;

    public PayStackController(UserPayStackPaymentService userPayStackPaymentService, SellerPayStackService sellerPayStackService, VideoPaymentService videoPaymentService) {
        this.userPayStackPaymentService = userPayStackPaymentService;
        this.sellerPayStackService = sellerPayStackService;
        this.videoPaymentService = videoPaymentService;
    }

    @GetMapping("user/verifyPayment/{orderId}")
    public ResponseEntity<CustomPaymentResponse> paymentVerificationForBuyers(@PathVariable(value = "orderId") Long orderId) throws Exception {
        if (orderId==null) {
            throw new DataNotAcceptableException("Order id must be provided in path");
        }
        return userPayStackPaymentService.verifyPayment(orderId);
    }

    @GetMapping("seller/verifyPayment/{productId}")
    public ResponseEntity<CustomPaymentResponse> paymentVerificationForSellers(@PathVariable(value = "productId") Long productId) throws Exception {
        if (productId==null) {
            throw new DataNotAcceptableException("Product id must be provided in path");
        }
        return sellerPayStackService.verifyPayment(productId);
    }

    @GetMapping("video/verifyPayment/{videoId}")
    public ResponseEntity<CustomPaymentResponse> videoPaymentVerification(@PathVariable("videoId")Long videoId) throws Exception {
        if (videoId==null) {
            throw new DataNotAcceptableException("Video id must be provided in path");
        }
        return videoPaymentService.verifyPayment(videoId);
    }

    @GetMapping("/all/products/order/payment")
    public UserServerResponse getAllBuyersPayment(HttpServletRequest request){
        return userPayStackPaymentService.getAllPayments(request);
    }
    @GetMapping("/user/all/payment/{userEmail}")
    public UserServerResponse getAllOnlinePaymentForUser(HttpServletRequest request,@PathVariable(value = "userEmail")String userEmail) throws Exception {
        if (userEmail.isEmpty()){
            throw new DataNotAcceptableException("User email required");
        }
        return userPayStackPaymentService.getAllPaymentsForUser(request,userEmail);
    }
    @GetMapping("/all/sellers/payment")
    public SellerServerPaymentResponse getAllSellersPayment(HttpServletRequest request){
        return sellerPayStackService.getAllPayment(request);
    }
    @GetMapping("/seller/all/payment/{sellerEmail}")
    public SellerServerPaymentResponse getAllOnlinePaymentForSeller(HttpServletRequest request,@PathVariable(value = "sellerEmail")String sellerEmail) throws Exception {
        if (sellerEmail.isEmpty()){
            throw new DataNotAcceptableException("Seller email required");
        }
        return sellerPayStackService.getAllPaymentForTheSeller(request,sellerEmail);
    }

    @GetMapping("/all/videos/payment")
    public VideoPaymentServerResponse getAllVideosPayment(HttpServletRequest request){
        return videoPaymentService.getAllPayment(request);
    }

    @GetMapping("/seller/all/video/payment/{sellerEmail}")
    public VideoPaymentServerResponse getAllVideoPaymentForSeller(HttpServletRequest request,@PathVariable(value = "sellerEmail")String sellerEmail) throws Exception {
        if (sellerEmail.isEmpty()){
            throw new DataNotAcceptableException("Seller email required");
        }
        return videoPaymentService.getAllPaymentForTheSeller(request,sellerEmail);
    }
}