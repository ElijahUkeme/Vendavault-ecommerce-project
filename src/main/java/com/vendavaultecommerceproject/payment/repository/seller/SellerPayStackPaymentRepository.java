package com.vendavaultecommerceproject.payment.repository.seller;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.payment.entity.seller.SellerPaymentStack;
import com.vendavaultecommerceproject.payment.model.seller.SellerPaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SellerPayStackPaymentRepository extends JpaRepository<SellerPaymentStack,Long> {

    List<SellerPaymentStack> findBySeller(SellerEntity seller);
    SellerPaymentStack findByProduct(ProductEntity product);
}
