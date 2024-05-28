package com.vendavaultecommerceproject.repository.products.image;

import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity,String> {
}
