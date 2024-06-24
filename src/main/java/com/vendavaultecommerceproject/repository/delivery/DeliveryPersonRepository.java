package com.vendavaultecommerceproject.repository.delivery;

import com.vendavaultecommerceproject.entities.delivery.DeliveryPersonEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPersonEntity,Long> {
    DeliveryPersonEntity findByPhoneNumber(String phoneNumber);
    DeliveryPersonEntity findByVehicleNumber(String vehicleNumber);
    List<DeliveryPersonEntity> findBySeller(SellerEntity seller);
}
