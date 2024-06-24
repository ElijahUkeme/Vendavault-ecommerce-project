package com.vendavaultecommerceproject.notification.model.user;


import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.model.user.Usermodel;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNotificationModel {

    private Long id;
    private String title;
    private String message;
    private Date dateCreated;
    private Date dateRead;
    private boolean isRead;
    private Usermodel user;
}
