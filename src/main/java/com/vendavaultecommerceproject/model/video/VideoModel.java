package com.vendavaultecommerceproject.model.video;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VideoModel {

    private Long id;
    private String title;
    private String videoUrl;
    private String paymentStatus;
    private String status;
    private LocalDate uploadedDate;
    private Date approvedDate;

    private SellerModel sellerModel;
}
