package com.vendavaultecommerceproject.repository.token;


import com.vendavaultecommerceproject.entities.token.UserAuthenticationTokenEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationTokenRepository extends JpaRepository<UserAuthenticationTokenEntity,Integer> {

    UserAuthenticationTokenEntity findByToken(String token);
    UserAuthenticationTokenEntity findByUser(UserEntity user);
}
