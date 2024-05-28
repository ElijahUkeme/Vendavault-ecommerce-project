package com.vendavaultecommerceproject.repository.cart;

import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<CartItemEntity,Long> {

    List<CartItemEntity> findByBuyer(UserEntity buyer);
}
