package com.vendavaultecommerceproject.notification.repository.seller;

import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.notification.entity.seller.SellerNotificationEntity;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SellerNotificationRepository extends JpaRepository<SellerNotificationEntity,Long> {
    List<SellerNotificationEntity> findBySeller(SellerEntity seller);
}
