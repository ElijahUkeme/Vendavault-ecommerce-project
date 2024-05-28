package com.vendavaultecommerceproject.entities.cart;


import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date addedDate;

    @ManyToOne
    @JoinColumn(nullable = false,name = "buyer_id")
    private UserEntity buyer;

    @ManyToOne
    @JoinColumn(nullable = false,name = "seller_id")
    private SellerEntity seller;

    @ManyToOne
    @JoinColumn(nullable = false,name = "product_id")
    private ProductEntity product;
    private int price;
    private int totalPrice;
    private int quantity;
    private boolean checkOut;
}
