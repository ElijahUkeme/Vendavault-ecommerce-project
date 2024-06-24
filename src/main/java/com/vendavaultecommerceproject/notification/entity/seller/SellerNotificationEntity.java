package com.vendavaultecommerceproject.notification.entity.seller;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
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
public class SellerNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private Date dateCreated;
    private Date dateRead;

    //checks if the notification has been read by the seller
    private boolean isRead;

    @ManyToOne(targetEntity = SellerEntity.class)
    @JoinColumn(nullable = false,name = "seller_id")
    private SellerEntity seller;
}
