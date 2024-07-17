package com.vendavaultecommerceproject.repository.products.product;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByProductName(String productName);

    @Query("select p from ProductEntity p where p.id = ?1")
    ProductEntity findByProductId(Long productId);

    //Retrieved all the product for a particular user whose status

    @Query("select p from ProductEntity p where p.productOwner =:seller")
    List<ProductEntity> getAllProductForSeller(SellerEntity seller);


    @Query("select * from ProductEntity p where p.productOwner =:seller")
    Page<ProductEntity> getAllProductForTheSellerWithPagination(SellerEntity seller, Pageable pageable);
     List<ProductEntity> findByUploadedDateBetween(LocalDate from, LocalDate to);
}
