package com.vendavaultecommerceproject.admin.report.controller;


import com.vendavaultecommerceproject.admin.report.response.OrderServerReportResponse;
import com.vendavaultecommerceproject.admin.report.service.SystemReportService;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SystemReportController {

    private final SystemReportService systemReportService;

    @GetMapping("/admin/monthly-sale-report")
    public OrderServerReportResponse getMonthlySaleReport(HttpServletRequest request){
        return systemReportService.getOrderReportForTheMonth(request);
    }

    @GetMapping("/admin/get-order-by-id")
    public OrderServerReportResponse getOrderBasedOnId(HttpServletRequest request, @RequestParam("orderId")Long orderId){
        return systemReportService.getOrderBasedOnId(request,orderId);
    }
    @PostMapping("/admin/registered-seller-for-the-month")
    public List<SellerModel> getRegisteredSellerForTheMonth(HttpServletRequest request){
        return systemReportService.getRegisteredSellerForTheMonth(request);
    }
    @PostMapping("/admin/registered-user-for-the-month")
    public List<Usermodel> getRegisteredUserForTheMonth(HttpServletRequest request){
        return systemReportService.getRegisteredUsersForTheMonth(request);
    }
}
