package com.vendavaultecommerceproject.payment.model.user;


import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.sales.SaleModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import com.vendavaultecommerceproject.utils.UserModelUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentModel {

    private Long id;
    private Usermodel user;
    private String accessCode;
    private SaleModel order;
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
