package com.vendavaultecommerceproject.model.cart;


import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartModel {


    private Long id;
    private Date addedDate;

    private Usermodel buyer;

    private ProductModel product;
    private int price;
    private int totalPrice;
    private int quantity;
}
