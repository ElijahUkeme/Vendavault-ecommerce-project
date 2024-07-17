package com.vendavaultecommerceproject.repository.seller;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SellerRepository extends JpaRepository<SellerEntity,Long> {

    SellerEntity findByEmail(String email);
    List<SellerEntity> findByCreatedDateBetween(LocalDate from, LocalDate to);
}
