package com.vendavaultecommerceproject.notification.repository.user;

import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity,Long> {
    List<UserNotificationEntity> findByUser(UserEntity user);
}
