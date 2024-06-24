package com.vendavaultecommerceproject.entities.delivery;


import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class DeliveryPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String vehicleNumber;
    private String vehicleType;
    @ManyToOne
    private SellerEntity seller;
}
