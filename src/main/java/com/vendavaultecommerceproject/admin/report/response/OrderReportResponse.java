package com.vendavaultecommerceproject.admin.report.response;

import com.vendavaultecommerceproject.admin.report.model.OrderReportModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportResponse {
    private int code;
    private String title;
    private String message;
    private double totalSale;
    private double totalProfit;
    private double totalCustomers;
    private List<OrderReportModel> data;
}
