package com.vendavaultecommerceproject.entities.order;

import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.util.Utility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
public class OrderTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date createdDate;
    private String token;

    @OneToOne(targetEntity = SaleEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private SaleEntity order;

    public OrderTokenEntity(){

    }

    public OrderTokenEntity(SaleEntity order){
        this.order = order;
        this.createdDate = new Date();
        this.token = Utility.orderToken();
    }
}
