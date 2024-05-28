package com.vendavaultecommerceproject.repository.products.product;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByProductName(String productName);

    @Query("select p from ProductEntity p where p.id = ?1")
    ProductEntity findByProductId(Long productId);

    //Retrieved all the product for a particular user whose status

    @Query("select p from ProductEntity p where p.productOwner =:seller")
    List<ProductEntity> getAllProductForSeller(SellerEntity seller);
}
