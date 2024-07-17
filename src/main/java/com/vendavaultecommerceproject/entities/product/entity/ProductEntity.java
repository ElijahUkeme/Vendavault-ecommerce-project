package com.vendavaultecommerceproject.entities.product.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String brand;
    private String category;
    private BigDecimal price;
    private String description;
    private String status;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date uploadedTime;
    private LocalDate uploadedDate;
    private Date approvedOrRejectedDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm")
    private Date updatedTime;
    private LocalDate updatedDate;
    private String paymentStatus;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_images",
                  joinColumns = {
                   @JoinColumn(name = "product_id")
                  },
                   inverseJoinColumns = @JoinColumn(name = "product_image_id"))
    private Set<ProductImageEntity> productImages;

    @ManyToOne(targetEntity = SellerEntity.class)
    @JoinColumn(nullable = false,name = "owner_id")
    private SellerEntity productOwner;
}
