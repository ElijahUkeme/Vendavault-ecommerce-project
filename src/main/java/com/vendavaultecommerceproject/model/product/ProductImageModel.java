package com.vendavaultecommerceproject.model.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageModel {

    private String id;
    private String fileType;
    private String fileName;
}
