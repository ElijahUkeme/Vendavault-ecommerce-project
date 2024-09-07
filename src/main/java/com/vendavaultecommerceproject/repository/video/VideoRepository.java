package com.vendavaultecommerceproject.repository.video;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Long> {


    @Query("select v from VideoEntity v where v.seller =:seller")
    List<VideoEntity> getAllVideosForSeller(SellerEntity seller);

    List<VideoEntity> findByUploadedDateBetween(LocalDate from, LocalDate to);

    @Query("SELECT s FROM VideoEntity s WHERE YEAR(s.uploadedDate) = :year AND MONTH(s.uploadedDate) = :month")
    List<VideoEntity> findVideoByMonthAndYearUploaded(@Param("year") int year, @Param("month") int month);

}
