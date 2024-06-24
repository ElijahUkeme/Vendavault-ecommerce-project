package com.vendavaultecommerceproject.utils;


import com.vendavaultecommerceproject.notification.entity.seller.SellerNotificationEntity;
import com.vendavaultecommerceproject.notification.entity.user.UserNotificationEntity;
import com.vendavaultecommerceproject.notification.model.seller.SellerNotificationModel;
import com.vendavaultecommerceproject.notification.model.user.UserNotificationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class SellerNotificationModelUtil {

    public static SellerNotificationModel getReturnedSellerNotificationModel(SellerNotificationEntity sellerNotificationEntity){
        SellerNotificationModel sellerNotificationModel = SellerNotificationModel.builder()
                .id(sellerNotificationEntity.getId())
                .title(sellerNotificationEntity.getTitle())
                .message(sellerNotificationEntity.getMessage())
                .dateCreated(sellerNotificationEntity.getDateCreated())
                .dateRead(sellerNotificationEntity.getDateRead())
                .isRead(sellerNotificationEntity.isRead())
                .sellerModel(SellerModelUtil.getReturnedSeller(sellerNotificationEntity.getSeller()))
                .build();
        return sellerNotificationModel;
    }
}
