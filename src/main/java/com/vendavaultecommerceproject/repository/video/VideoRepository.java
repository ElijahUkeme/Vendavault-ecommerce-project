package com.vendavaultecommerceproject.repository.video;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Long> {


    @Query("select v from VideoEntity v where v.seller =:seller")
    List<VideoEntity> getAllVideosForSeller(SellerEntity seller);
}
