package com.vendavaultecommerceproject.service.main.products.product;

import com.vendavaultecommerceproject.dto.product.ApprovedOrRejectProductDto;
import com.vendavaultecommerceproject.dto.product.UpdateProductAfterSaleDto;
import com.vendavaultecommerceproject.dto.product.UpdateProductDto;
import com.vendavaultecommerceproject.dto.product.UploadProductDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.pagination.ProductPageResponse;
import com.vendavaultecommerceproject.response.product.ProductServerListResponse;
import com.vendavaultecommerceproject.response.product.ProductServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ResponseEntity<CustomPaymentResponse> uploadProduct(UploadProductDto uploadProductDto, MultipartFile file, HttpServletRequest request) throws Exception;
    ProductServerResponse approveOrRejectProductByAdmin(ApprovedOrRejectProductDto approvedOrRejectProductDto,HttpServletRequest request);
    ProductServerResponse updateProduct(UpdateProductDto updateProductDto,HttpServletRequest request);
    //String updateProductAfterSale(UpdateProductAfterSaleDto updateProductAfterSaleDto, HttpServletRequest request);
    ProductPageResponse getAllProductWithPagination(Integer pageNumber,Integer pageSize);
    ProductPageResponse getAllProductWithPaginationAndSorting(Integer pageNumber,Integer pageSize,String sortBy, String sortDirection);
    ProductServerListResponse getAllApprovedProductForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request);
    ProductServerListResponse getAllPendingProductForTheSeller(RetrieveUserDto retrieveUserDto,HttpServletRequest request);
    ProductServerListResponse getAllUploadedProductForTheSeller(RetrieveUserDto retrieveUserDto,HttpServletRequest request);
    ProductServerListResponse getAllProductInTheStockForTheSeller(RetrieveUserDto retrieveUserDto,HttpServletRequest request);
}
