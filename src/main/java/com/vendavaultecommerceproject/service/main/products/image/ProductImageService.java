package com.vendavaultecommerceproject.service.main.products.image;

import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface ProductImageService {

    public ProductImageEntity saveProductImage (MultipartFile file) throws Exception;
    public ProductImageEntity getProductImage(String productId) throws Exception;
    public Set<ProductImageEntity> uploadProduct(MultipartFile[] files) throws DataNotAcceptableException, IOException;
}
