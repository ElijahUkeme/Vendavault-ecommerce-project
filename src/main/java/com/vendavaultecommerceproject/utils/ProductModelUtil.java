package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.model.product.ProductModel;

public class ProductModelUtil {

    public static ProductModel getReturnedProductModel(ProductEntity product){

        ProductModel productModel = ProductModel.builder()
                .id(product.getId())
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
                .uploadedTime(product.getUploadedTime())
                .uploadedDate(product.getUploadedDate())
                .price(product.getPrice())
                .productImages(ProductImageModelUtil.getReturnedProductImageModel(product.getProductImages()))
                .build();

        return productModel;

    }
}
