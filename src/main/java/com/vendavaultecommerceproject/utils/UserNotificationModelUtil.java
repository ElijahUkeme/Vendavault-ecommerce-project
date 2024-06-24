package com.vendavaultecommerceproject.utils;


import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class UserNotificationModelUtil {

    public static UserNotificationModel getReturnedUserNotificationModel(UserNotificationEntity userNotification){
        UserNotificationModel userNotificationModel = UserNotificationModel.builder()
                .id(userNotification.getId())
                .title(userNotification.getTitle())
                .message(userNotification.getMessage())
                .dateCreated(userNotification.getDateCreated())
                .dateRead(userNotification.getDateRead())
                .isRead(userNotification.isRead())
                .user(UserModelUtil.getReturnedUserModel(userNotification.getUser()))
                .build();
        return userNotificationModel;
    }
}
