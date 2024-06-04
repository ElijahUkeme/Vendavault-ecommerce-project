package com.vendavaultecommerceproject.payment.model.video;


import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.video.VideoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoUploadPaymentModel {
    private Long id;
    private SellerModel seller;
    private String accessCode;
    private VideoModel videoModel;
    private String reference;
    private double amount;
    private String status;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String ipAddress;
}
