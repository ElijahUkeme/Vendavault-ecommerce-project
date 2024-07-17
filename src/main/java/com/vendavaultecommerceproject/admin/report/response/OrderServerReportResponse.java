package com.vendavaultecommerceproject.admin.report.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderServerReportResponse {

    private String terminus;
    private String status;
    private OrderReportResponse response;
}
