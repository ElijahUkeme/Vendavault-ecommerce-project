package com.vendavaultecommerceproject.repository.operation.user;

import com.vendavaultecommerceproject.entities.operation.seller.SellerAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.operation.user.UserAccountSuspensionEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminUserAccountSuspensionRepository extends JpaRepository<UserAccountSuspensionEntity,Long> {

    List<UserAccountSuspensionEntity> findByUser(UserEntity user);
}
