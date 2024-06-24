package com.vendavaultecommerceproject.utils;

import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.model.product.ProductImageModel;

import java.util.HashSet;
import java.util.Set;

public class ProductImageModelUtil {

    public static Set<ProductImageModel> getReturnedProductImageModel(Set<ProductImageEntity>productImageEntitySet){
        Set<ProductImageModel> productImageModels = new HashSet<>();
        for (ProductImageEntity productImage: productImageEntitySet){
            ProductImageModel productImageModel = ProductImageModel.builder()
                    .id(productImage.getId())
                    .fileName(productImage.getFileName())
                    .fileType(productImage.getFileType())
                    .build();
            productImageModels.add(productImageModel);
        }
        return productImageModels;
    }
}
