package com.vendavaultecommerceproject.repository.token;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.token.SellerAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerAuthenticationTokenRepository extends JpaRepository<SellerAuthenticationTokenEntity,Integer> {

    SellerAuthenticationTokenEntity findByToken(String token);
    SellerAuthenticationTokenEntity findBySeller(SellerEntity seller);
}
