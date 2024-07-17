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
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


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
    private LocalDate uploadedDate;
    private Date uploadedTime;
    private Date updatedTime;
    private Date approvedOrRejectedDate;
    private LocalDate updatedDate;
    private Set<ProductImageModel> productImages;
    private SellerModel sellerModel;

}
