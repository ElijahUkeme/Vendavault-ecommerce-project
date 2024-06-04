package com.vendavaultecommerceproject.entities.seller;


import com.vendavaultecommerceproject.entities.password.ForgotPasswordEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class SellerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String businessName;
    private boolean isVerified;
    private String phoneNumber;
    private String accountStatus;
    private String businessDescription;
    private String identificationUrl;
    @OneToOne(mappedBy = "seller")
    private ForgotPasswordEntity passwordEntity;
}
