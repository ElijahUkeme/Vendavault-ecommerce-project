package com.vendavaultecommerceproject.entities.product.entity;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ProductEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String brand;
    private String category;
    private BigDecimal price;
    private String description;
    private String status;
    private Date uploadedDate;
    private Date approvedOrRejectedDate;
    private Date updatedDate;
    private String paymentStatus;
    private String productImage;

    @ManyToOne(targetEntity = SellerEntity.class)
    @JoinColumn(nullable = false,name = "owner_id")
    private SellerEntity productOwner;
}
