package com.vendavaultecommerceproject.notification.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date orderedTime;
    private LocalDate orderedDate;
    private List<CartItemEntity> cartItems;

}
