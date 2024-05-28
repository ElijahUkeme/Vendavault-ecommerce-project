package com.vendavaultecommerceproject.payment.model.seller;


import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.util.enums.SubscriptionPlanPricing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SellerPaymentModel {

    private Long id;
    private SellerModel seller;
    private String accessCode;
    private ProductModel product;
    private String reference;
    private double amount;
    private String status;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String ipAddress;
}
