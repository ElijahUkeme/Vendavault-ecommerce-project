package com.vendavaultecommerceproject.notification.model.seller;


import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerNotificationModel {

    private Long id;
    private String title;
    private String message;
    private Date dateCreated;
    private Date dateRead;
    private boolean isRead;
    private SellerModel sellerModel;
}
