package com.vendavaultecommerceproject.notification.repository.admin;

import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.notification.entity.admin.AdminNotificationEntity;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminNotificationRepository extends JpaRepository<AdminNotificationEntity,Long> {

    List<AdminNotificationEntity> findByAdmin(AdminRegistrationEntity admin);
}
