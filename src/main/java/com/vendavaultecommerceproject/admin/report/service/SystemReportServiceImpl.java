package com.vendavaultecommerceproject.admin.report.service;


import com.vendavaultecommerceproject.admin.report.dto.MonthlyReportDto;
import com.vendavaultecommerceproject.admin.report.model.OrderReportModel;
import com.vendavaultecommerceproject.admin.report.model.SystemReportModel;
import com.vendavaultecommerceproject.admin.report.response.OrderReportResponse;
import com.vendavaultecommerceproject.admin.report.response.OrderServerReportResponse;
import com.vendavaultecommerceproject.admin.report.utils.MonthlyReportUtil;
import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.model.sales.SaleModel;
import com.vendavaultecommerceproject.model.seller.SellerModel;
import com.vendavaultecommerceproject.model.user.Usermodel;
import com.vendavaultecommerceproject.repository.products.product.ProductRepository;
import com.vendavaultecommerceproject.repository.sale.SaleRepository;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import com.vendavaultecommerceproject.repository.video.VideoRepository;
import com.vendavaultecommerceproject.utils.SellerModelUtil;
import com.vendavaultecommerceproject.utils.UserModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SystemReportServiceImpl implements SystemReportService{

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public OrderServerReportResponse getOrderReportForTheMonth(HttpServletRequest request) {
        if (!MonthlyReportUtil.isLastDayOfTheMonth()){
           return new OrderServerReportResponse(
                   baseUrl+request.getRequestURI(),"OK",
                   new OrderReportResponse(200,"Monthly Sale report","Report can only be shown on the last day of the month",0,0,0,null));
        }
        return new OrderServerReportResponse(
                baseUrl+request.getRequestURI(),"OK",
                new OrderReportResponse(200,"Monthly Sale report",returnMonthAsString(),
                        getMonthlySale().size(),returnTotalProfitForTheMonth(),
                        returnTotalRegisteredCustomersForTheMonth(),getMonthlySale()));
    }
    @Override
    public OrderServerReportResponse getOrderBasedOnId(HttpServletRequest request, Long orderId) {
        if (Objects.isNull(saleRepository.findById(orderId))){
            return new OrderServerReportResponse(
                    baseUrl+request.getRequestURI(),"NOT OK",
                    new OrderReportResponse(406,"Order report","Order Id not found",0,0,0,null));
        }
        return new OrderServerReportResponse(
                baseUrl+request.getRequestURI(),"OK",
                new OrderReportResponse(200,"Order report","Order with Id "+orderId,0,0,0,getOrderBasedOnId(orderId)));
    }

    @Override
    public OrderServerReportResponse getOtherReportBasedOnSelectedMonth(HttpServletRequest request, MonthlyReportDto monthlyReportDto) throws DataNotAcceptableException {
        int year = monthlyReportDto.getYear();
        int month = monthlyReportDto.getMonth();
        if (Objects.isNull(year)){
            throw new DataNotAcceptableException("Please Enter a year");
        }
        if (Objects.isNull(month)){
            throw new DataNotAcceptableException("Please Enter a month");
        }
        return new OrderServerReportResponse(
                baseUrl+request.getRequestURI(),"OK",
                new OrderReportResponse(200,"Past Month Report",returnSelectedMonthAsString(year, month),
                        getPreviousMonthSale(year, month).size(),returnTotalProfitForTheMonthAndYearSelected(year,month)
                        ,returnTotalRegisteredCustomersForTheMonthAndYearSelected(year,month),
                        getPreviousMonthSale(year,month)));
    }
    @Override
    public List<SellerModel> getRegisteredSellerForTheMonth(HttpServletRequest request) {
        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = YearMonth.now().atEndOfMonth();
        List<SellerModel> sellerModels = new ArrayList<>();
        List<SellerEntity> sellerEntities = sellerRepository.findByCreatedDateBetween(start,end);
        for (SellerEntity seller: sellerEntities){
            sellerModels.add(SellerModelUtil.getReturnedSeller(seller));
        }
        return sellerModels;
    }
    @Override
    public List<Usermodel> getRegisteredUsersForTheMonth(HttpServletRequest request) {
        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = YearMonth.now().atEndOfMonth();
        List<Usermodel> usermodels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByCreatedDateBetween(start,end);
        for (UserEntity user: userEntities){
            usermodels.add(UserModelUtil.getReturnedUserModel(user));
        }
        return usermodels;
    }

    private double returnTotalProfitForTheMonth(){
        double totalVideoUpload = 0;
        double totalProductUpload = 0;
        double totalProfit = 0;

        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = YearMonth.now().atEndOfMonth();
        List<ProductEntity> productEntities =
                productRepository.findByUploadedDateBetween(start,end);
        totalProductUpload = productEntities.size();

        List<VideoEntity> videoEntities = videoRepository.findByUploadedDateBetween(start,end);
        totalVideoUpload = videoEntities.size();

        totalProfit = ((totalProductUpload + totalVideoUpload)*1000);

        return totalProfit;
    }

    private double returnTotalProfitForTheMonthAndYearSelected(int year,int month){
        double totalVideoUpload = 0;
        double totalProductUpload = 0;
        double totalProfit = 0;

        List<ProductEntity> productEntities =
                productRepository.findProductByMonthAndYearUploaded(year,month);
        totalProductUpload = productEntities.size();

        List<VideoEntity> videoEntities = videoRepository.findVideoByMonthAndYearUploaded(year,month);
        totalVideoUpload = videoEntities.size();

        totalProfit = ((totalProductUpload + totalVideoUpload)*1000);

        return totalProfit;
    }

    private double returnTotalRegisteredCustomersForTheMonth(){
        double newRegisteredSellers = 0;
        double newRegisteredBuyers = 0;
        double totalNewCustomers = 0;

        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = YearMonth.now().atEndOfMonth();
        List<UserEntity> userEntities =
                userRepository.findByCreatedDateBetween(start,end);
        newRegisteredBuyers = userEntities.size();

        List<SellerEntity> sellerEntities =
                sellerRepository.findByCreatedDateBetween(start,end);
        newRegisteredSellers = sellerEntities.size();

        totalNewCustomers = newRegisteredBuyers + newRegisteredSellers;

        return totalNewCustomers;

    }

    private double returnTotalRegisteredCustomersForTheMonthAndYearSelected(int year,int month){
        double newRegisteredSellers = 0;
        double newRegisteredBuyers = 0;
        double totalNewCustomers = 0;


        List<UserEntity> userEntities =
                userRepository.findUsersByMonthAndYearCreated(year,month);
        newRegisteredBuyers = userEntities.size();

        List<SellerEntity> sellerEntities =
                sellerRepository.findSellersByMonthAndYearCreated(year,month);
        newRegisteredSellers = sellerEntities.size();

        totalNewCustomers = newRegisteredBuyers + newRegisteredSellers;

        return totalNewCustomers;

    }


    private List<OrderReportModel> getMonthlySale(){
        //Only retrieved sales made in that particular month
        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = YearMonth.now().atEndOfMonth();
        List<SaleEntity> saleEntities =
                saleRepository.findByDatePurchasedBetween(start,end);
        //Assign the retrieved sales to orderModel
        List<OrderReportModel> orderReportModels = new ArrayList<>();
        for (SaleEntity sale: saleEntities){
            List<CartItemEntity> cartItemEntityList = sale.getCartItemList().stream().toList();
            for (CartItemEntity cartItemEntity : cartItemEntityList){
                OrderReportModel orderReportModel = OrderReportModel.builder()
                        .orderId(sale.getId())
                        .productName(cartItemEntity.getProduct().getProductName())
                        .price(cartItemEntity.getProduct().getPrice().intValue())
                        .date(sale.getDatePurchased().toString())
                        .vendor(cartItemEntity.getProduct().getProductOwner().getName())
                        .productImage(cartItemEntity.getProduct().getProductImages().stream().toList().toString())
                        .build();
                orderReportModels.add(orderReportModel);
            }
        }
        return orderReportModels;
    }

    private List<OrderReportModel> getPreviousMonthSale(int year,int month){
        //Only retrieved sales made in that particular month and year selected
        List<SaleEntity> saleEntities =
                saleRepository.findSalesByMonthAndYear(year,month);
        //Assign the retrieved sales to orderModel
        List<OrderReportModel> orderReportModels = new ArrayList<>();
        for (SaleEntity sale: saleEntities){
            List<CartItemEntity> cartItemEntityList = sale.getCartItemList().stream().toList();
            for (CartItemEntity cartItemEntity : cartItemEntityList){
                OrderReportModel orderReportModel = OrderReportModel.builder()
                        .orderId(sale.getId())
                        .productName(cartItemEntity.getProduct().getProductName())
                        .price(cartItemEntity.getProduct().getPrice().intValue())
                        .date(sale.getDatePurchased().toString())
                        .vendor(cartItemEntity.getProduct().getProductOwner().getName())
                        .productImage(cartItemEntity.getProduct().getProductImages().stream().toList().toString())
                        .build();
                orderReportModels.add(orderReportModel);
            }
        }
        return orderReportModels;
    }


    private List<OrderReportModel> getOrderBasedOnId(Long orderId){
        SaleEntity sale = saleRepository.findById(orderId).get();
        List<OrderReportModel> orderReportModels = new ArrayList<>();
        List<CartItemEntity> cartItemEntityList = sale.getCartItemList().stream().toList();
        for (CartItemEntity cartItemEntity : cartItemEntityList){
            OrderReportModel orderReportModel = OrderReportModel.builder()
                    .orderId(sale.getId())
                    .productName(cartItemEntity.getProduct().getProductName())
                    .price(cartItemEntity.getProduct().getPrice().intValue())
                    .date(sale.getDatePurchased().toString())
                    .vendor(cartItemEntity.getProduct().getProductOwner().getName())
                    .productImage(cartItemEntity.getProduct().getProductImages().stream().toList().toString())
                    .build();
            orderReportModels.add(orderReportModel);
        }
        return orderReportModels;
    }

    private String returnMonthAsString(){
        LocalDate givenDate = LocalDate.now();
        // Create a DateTimeFormatter object to format LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yyyy");
        DateTimeFormatter formatterWithDay = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

        // Use the format() method to convert LocalDate to string
        String dateToString = givenDate.format(formatter);
        String dayToString = givenDate.format(formatterWithDay);
        LocalDate localDate = LocalDate.parse(givenDate.format(formatter));
        System.out.println("The formatted date is "+localDate);
        String fullString = "The report for the month of "+dateToString+" generated on "+dayToString;

        // Return the result
        return (fullString);
    }
    private String returnSelectedMonthAsString(int year,int month){
        LocalDate selectedDate = LocalDate.of(year,month,1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-yyyy");
        DateTimeFormatter formatterWithDay = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

        // Use the format() method to convert LocalDate to string
        String dateToString = selectedDate.format(formatter);
        String dayToString = selectedDate.format(formatterWithDay);
        //LocalDate localDate = LocalDate.parse(selectedDate.format(formatter));
        //System.out.println("The formatted date is "+localDate);
        String fullString = "The report for the month of "+dateToString;

        // Return the result
        return (fullString);
    }
}
