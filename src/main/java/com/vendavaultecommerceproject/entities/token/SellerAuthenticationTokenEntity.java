package com.vendavaultecommerceproject.entities.token;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class SellerAuthenticationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date createdDate;
    private String token;

    @OneToOne(targetEntity = SellerEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "seller_id")
    private SellerEntity seller;

    public SellerAuthenticationTokenEntity(){

    }

    public SellerAuthenticationTokenEntity(SellerEntity seller){
        this.seller = seller;
        this.createdDate = new Date();
        this.token = UUID.randomUUID().toString();
    }
}
