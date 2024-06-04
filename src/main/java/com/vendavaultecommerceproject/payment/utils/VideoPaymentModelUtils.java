package com.vendavaultecommerceproject.payment.utils;

import com.vendavaultecommerceproject.payment.entity.seller.SellerPaymentStack;
import com.vendavaultecommerceproject.payment.entity.video.VideoUploadPaymentEntity;
import com.vendavaultecommerceproject.payment.model.seller.SellerPaymentModel;
import com.vendavaultecommerceproject.payment.model.video.VideoUploadPaymentModel;
import com.vendavaultecommerceproject.utils.ProductModelUtil;
import com.vendavaultecommerceproject.utils.SellerModelUtil;
import com.vendavaultecommerceproject.utils.VideoModelUtil;

public class VideoPaymentModelUtils {

    public static VideoUploadPaymentModel getReturnedVideoPaymentUtil(VideoUploadPaymentEntity videoUploadPayment) {
        VideoUploadPaymentModel videoUploadPaymentModel = VideoUploadPaymentModel.builder()
                .id(videoUploadPayment.getId())
                .paidAt(videoUploadPayment.getPaidAt())
                .accessCode(videoUploadPayment.getAccessCode())
                .amount(videoUploadPayment.getAmount())
                .channel(videoUploadPayment.getChannel())
                .createdAt(videoUploadPayment.getCreatedAt())
                .currency(videoUploadPayment.getCurrency())
                .gatewayResponse(videoUploadPayment.getGatewayResponse())
                .ipAddress(videoUploadPayment.getIpAddress())
                .videoModel(VideoModelUtil.getReturnedVideoModel(videoUploadPayment.getVideo()))
                .reference(videoUploadPayment.getReference())
                .status(videoUploadPayment.getStatus())
                .seller(SellerModelUtil.getReturnedSeller(videoUploadPayment.getSeller()))
                .build();
        return videoUploadPaymentModel;
    }
}
