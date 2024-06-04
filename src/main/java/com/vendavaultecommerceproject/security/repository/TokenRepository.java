package com.vendavaultecommerceproject.security.repository;


import com.vendavaultecommerceproject.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByToken(String token);
}
