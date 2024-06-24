package com.vendavaultecommerceproject.notification.model;

import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationModel {

    private Long orderId;
    private String deliveredPersonName;
    private String deliveredPersonPhone;
    private String deliveredPersonAddress;
    private String paymentStatus;
    private double totalAmount;
    private Date orderedDate;
    private List<CartItemEntity> cartItems;

}
