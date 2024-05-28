package com.vendavaultecommerceproject.model.sales;


import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.cart.CartModel;
import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
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
@Builder
public class SaleModel {

    private Long id;
    private String status;
    private String paymentType;
    private Usermodel buyer;
    private SellerModel seller;
    private String deliveredPersonName;
    private List<CartModel> cartModels;
    private BigDecimal totalPrice;
    private Date datePurchased;
}
