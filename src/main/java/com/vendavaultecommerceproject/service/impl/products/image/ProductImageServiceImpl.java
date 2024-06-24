package com.vendavaultecommerceproject.service.impl.products.image;

import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.repository.products.image.ProductImageRepository;
import com.vendavaultecommerceproject.service.main.products.image.ProductImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Service
public class ProductImageServiceImpl implements ProductImageService {
    private ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

//    void todo(MultipartFile[]files){
//        try {
//            Set<ProductImageEntity> images = uploadImage(files);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//    }


    @Override
    public ProductImageEntity saveProductImage(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new DataNotAcceptableException("File Name Contains invalid character");
        } else {
            ProductImageEntity productImageEntity = new ProductImageEntity(file.getContentType(), fileName, file.getBytes());
            return productImageRepository.save(productImageEntity);
        }
    }

    @Override
    public ProductImageEntity getProductImage(String productId) throws Exception {
        return productImageRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product Image Id not found"));
    }

    @Override
    public Set<ProductImageEntity> uploadProduct(MultipartFile[] files) throws DataNotAcceptableException, IOException {


        Set<ProductImageEntity> imageEntitySet = new HashSet<>();
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new DataNotAcceptableException("There is a file with invalid character");
            }
            if (files.length > 5) {
                //maximum file upload for a product is 5 images
                throw new DataNotAcceptableException("One product can only have a maximum of 5 images");
            } else if (files.length < 2) {
                //minimum file upload for a product is 2
                throw new DataNotAcceptableException("You must upload at least 2 images for a given product");
            } else {
                ProductImageEntity productImage = new ProductImageEntity(
                        file.getContentType(), file.getOriginalFilename(), file.getBytes()
                );
                productImageRepository.save(productImage);
                imageEntitySet.add(productImage);
            }
        }
        return imageEntitySet;

    }
}
