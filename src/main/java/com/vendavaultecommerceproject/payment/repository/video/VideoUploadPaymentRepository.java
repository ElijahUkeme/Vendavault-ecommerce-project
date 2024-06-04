package com.vendavaultecommerceproject.payment.repository.video;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import com.vendavaultecommerceproject.payment.entity.video.VideoUploadPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoUploadPaymentRepository extends JpaRepository<VideoUploadPaymentEntity,Long> {

    List<VideoUploadPaymentEntity> findBySeller(SellerEntity seller);
    VideoUploadPaymentEntity findByVideo(VideoEntity video);
}
