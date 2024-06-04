package com.vendavaultecommerceproject.cotroller.sale;


import com.vendavaultecommerceproject.dto.sale.SaleDto;
import com.vendavaultecommerceproject.dto.sale.UpdateSaleDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.sale.SaleListServerResponse;
import com.vendavaultecommerceproject.response.sale.SaleServerResponse;
import com.vendavaultecommerceproject.service.main.sale.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {

    private SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }


    @PostMapping("/sales/order/checkOut")
    public ResponseEntity<CustomPaymentResponse> checkOut(@RequestBody SaleDto saleDto){
        return saleService.saleProduct(saleDto);
    }
    @PostMapping("sales/all/for/seller")
    public SaleListServerResponse allSalesForTheSeller(@RequestBody RetrieveUserDto retrieveUserDto,HttpServletRequest request){
        return saleService.getSaleForTheSeller(retrieveUserDto,request);
    }
    @GetMapping("/sales/all")
    public SaleListServerResponse allSalesMade(HttpServletRequest request){
        return saleService.getAllSales(request);
    }


    @PostMapping("/sales/delivery/order/update")
    SaleServerResponse updateOrder(@RequestBody UpdateSaleDto updateSaleDto,HttpServletRequest request) throws DataNotFoundException {
        return saleService.confirmOrder(updateSaleDto,request);
    }
}

