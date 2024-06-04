package com.vendavaultecommerceproject.repository.operation.seller;

import com.vendavaultecommerceproject.entities.operation.seller.SellerAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminSellerAccountSuspensionRepository extends JpaRepository<SellerAccountSuspensionEntity,Long> {

    List<SellerAccountSuspensionEntity> findBySeller(SellerEntity seller);
}
