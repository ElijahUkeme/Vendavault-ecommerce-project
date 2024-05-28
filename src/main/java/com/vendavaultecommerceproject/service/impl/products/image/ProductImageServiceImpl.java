package com.vendavaultecommerceproject.service.impl.products.image;

import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.repository.products.image.ProductImageRepository;
import com.vendavaultecommerceproject.service.main.products.image.ProductImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ProductImageServiceImpl implements ProductImageService {
   private ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductImageEntity saveProductImage(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new Exception("File Name Contains invalid character");
        } else {
            ProductImageEntity productImageEntity = new ProductImageEntity(file.getContentType(), fileName, file.getBytes());
            return productImageRepository.save(productImageEntity);
        }
    }

        @Override
        public ProductImageEntity getProductImage(String productId) throws Exception {
            return productImageRepository.findById(productId)
                    .orElseThrow(()->new Exception("Product Image Id not found"));
        }
}
