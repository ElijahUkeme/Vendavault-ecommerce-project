package com.vendavaultecommerceproject.dto.product;

import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadProductDto {

    private String productName;
    private String brand;
    private String category;
    private BigDecimal price;
    private String description;
    String sellerEMail;
}
