package com.vendavaultecommerceproject.admin.repository;

import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRegistrationRepository extends JpaRepository<AdminRegistrationEntity,Long> {

    AdminRegistrationEntity findByEmail(String email);
}
