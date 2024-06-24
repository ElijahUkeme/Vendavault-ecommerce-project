package com.vendavaultecommerceproject.repository.sale;

import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {

    //List<SaleEntity> findBySeller(SellerEntity seller);
}
