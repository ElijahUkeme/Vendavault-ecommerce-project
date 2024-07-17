package com.vendavaultecommerceproject.entities.sale;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.util.DateTime;
import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.entities.order.OrderTokenEntity;
import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String paymentType;
    private String deliveredPersonName;
    private String deliveredAddress;
    private String deliveredPhone;
    private String paymentStatus;
    @ManyToOne
    private UserEntity buyer;
    private double totalPrice;
    private LocalDate datePurchased;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date timePurchased;
    @OneToMany
    private List<CartItemEntity> cartItemList;
}
