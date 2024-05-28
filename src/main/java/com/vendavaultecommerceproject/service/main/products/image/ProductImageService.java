package com.vendavaultecommerceproject.service.main.products.image;

import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {

    public ProductImageEntity saveProductImage (MultipartFile file) throws Exception;
    public ProductImageEntity getProductImage(String productId) throws Exception;
}
