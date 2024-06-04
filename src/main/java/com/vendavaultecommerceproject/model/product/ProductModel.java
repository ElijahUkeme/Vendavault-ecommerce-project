package com.vendavaultecommerceproject.model.product;

import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductModel {

    private Long id;
    private String productName;
    private String brand;
    private String category;
    private BigDecimal price;
    private String description;
    private String status;
    private String paymentStatus;
    private Date uploadedDate;
    private Date approvedOrRejectedDate;
    private Date updatedDate;
    private String productImage;
    private SellerModel sellerModel;
}
