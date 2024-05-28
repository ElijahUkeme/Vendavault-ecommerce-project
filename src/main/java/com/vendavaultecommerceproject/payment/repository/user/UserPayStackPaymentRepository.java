package com.vendavaultecommerceproject.payment.repository.user;

import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.payment.entity.user.UserPaymentStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPayStackPaymentRepository extends JpaRepository<UserPaymentStack,Long> {

    UserPaymentStack findByOrder(SaleEntity sale);
    List<UserPaymentStack> findByUser(UserEntity user);
}
