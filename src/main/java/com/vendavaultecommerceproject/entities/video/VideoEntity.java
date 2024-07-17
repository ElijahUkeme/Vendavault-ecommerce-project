package com.vendavaultecommerceproject.entities.video;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String videoUrl;
    private String status;
    private String paymentStatus;
    private Date approvedDate;
    private LocalDate uploadedDate;
    @ManyToOne(targetEntity = SellerEntity.class)
    @JoinColumn(nullable = false,name = "seller_id")
    private SellerEntity seller;

}
