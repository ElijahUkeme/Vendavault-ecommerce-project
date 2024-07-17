package com.vendavaultecommerceproject.admin.report.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReportModel {

    private Long orderId;
    private String productName;
    private String productImage;
    private int price;
    private int quantity;
    private String vendor;
    private String date;
}
