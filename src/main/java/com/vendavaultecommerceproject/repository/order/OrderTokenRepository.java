package com.vendavaultecommerceproject.repository.order;

import com.vendavaultecommerceproject.entities.order.OrderTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderTokenRepository extends JpaRepository<OrderTokenEntity,Integer> {
    OrderTokenEntity findByToken(String token);
}
