package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.video.VideoEntity;
import com.vendavaultecommerceproject.model.video.VideoModel;

public class VideoModelUtil {

    public static VideoModel getReturnedVideoModel(VideoEntity video){

        VideoModel videoModel = VideoModel.builder()
                .id(video.getId())
                .title(video.getTitle())
                .videoUrl(video.getVideoUrl())
                .status(video.getStatus())
                .paymentStatus(video.getPaymentStatus())
                .sellerModel(VideoModelUtil.getReturnedVideoModel(video).getSellerModel())
                .uploadedDate(video.getUploadedDate())
                .approvedDate(video.getApprovedDate())
                .build();
        return videoModel;
    }
}
