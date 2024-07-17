package com.vendavaultecommerceproject.admin.auth.repository;

import com.vendavaultecommerceproject.admin.auth.entity.AdminRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRegistrationRepository extends JpaRepository<AdminRegistrationEntity,Long> {

    AdminRegistrationEntity findByEmail(String email);
}
