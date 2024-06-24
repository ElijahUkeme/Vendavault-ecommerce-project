package com.vendavaultecommerceproject.notification.entity.admin;


import com.vendavaultecommerceproject.admin.entity.AdminRegistrationEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AdminNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private Date dateCreated;
    private Date dateRead;

    //checks if the notification has been read by the user
    private boolean isRead;
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(nullable = false,name = "admin_id")
    private AdminRegistrationEntity admin;
}
