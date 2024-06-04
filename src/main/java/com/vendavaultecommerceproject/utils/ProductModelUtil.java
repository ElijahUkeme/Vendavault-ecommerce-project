package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.model.product.ProductModel;

public class ProductModelUtil {

    public static ProductModel getReturnedProductModel(ProductEntity product){

        ProductModel productModel = ProductModel.builder()
                .id(product.getId())
                .productImage(product.getProductImage())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .sellerModel(SellerModelUtil.getReturnedSeller(product.getProductOwner()))
                .approvedOrRejectedDate(product.getApprovedOrRejectedDate())
                .category(product.getCategory())
                .description(product.getDescription())
                .status(product.getStatus())
                .paymentStatus(product.getPaymentStatus())
                .updatedDate(product.getUpdatedDate())
                .uploadedDate(product.getUploadedDate())
                .price(product.getPrice())
                .build();

        return productModel;

    }
}
