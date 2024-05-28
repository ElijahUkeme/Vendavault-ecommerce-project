package com.vendavaultecommerceproject.repository.password;

import com.vendavaultecommerceproject.entities.password.ForgotPasswordEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity,Integer> {

    @Query("select p from ForgotPasswordEntity p where p.code = ?1 and p.user = ?2")
    ForgotPasswordEntity findByCodeAndUser(int code, UserEntity user);
    ForgotPasswordEntity findByCode(int code);
}
