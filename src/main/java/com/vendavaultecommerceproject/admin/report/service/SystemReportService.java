package com.vendavaultecommerceproject.admin.report.service;

import com.vendavaultecommerceproject.admin.report.response.OrderServerReportResponse;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface SystemReportService {

    OrderServerReportResponse getOrderReportForTheMonth(HttpServletRequest request);
    OrderServerReportResponse getOrderBasedOnId(HttpServletRequest request, Long orderId);
    OrderServerReportResponse getOtherReportBasedOnSelectedMonth(HttpServletRequest request,String pickedMonth);
    List<SellerModel> getRegisteredSellerForTheMonth(HttpServletRequest request);
    List<Usermodel> getRegisteredUsersForTheMonth(HttpServletRequest request);
}
