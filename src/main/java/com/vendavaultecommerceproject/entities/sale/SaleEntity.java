package com.vendavaultecommerceproject.entities.sale;


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

import java.math.BigDecimal;
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
    private Date datePurchased;
    @OneToMany
    private List<CartItemEntity> cartItemList;
}
