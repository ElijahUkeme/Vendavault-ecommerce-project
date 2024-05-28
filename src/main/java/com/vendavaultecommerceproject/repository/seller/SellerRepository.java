package com.vendavaultecommerceproject.repository.seller;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SellerRepository extends JpaRepository<SellerEntity,Long> {

    SellerEntity findByEmail(String email);
}
