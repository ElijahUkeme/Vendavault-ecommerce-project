package com.vendavaultecommerceproject.model.sales;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.cart.CartModel;
import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    private LocalDate datePurchased;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date timePurchased;
}
