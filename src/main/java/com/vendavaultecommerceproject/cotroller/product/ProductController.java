package com.vendavaultecommerceproject.cotroller.product;


import com.vendavaultecommerceproject.dto.product.ApprovedOrRejectProductDto;
import com.vendavaultecommerceproject.dto.product.UpdateProductDto;
import com.vendavaultecommerceproject.dto.product.UploadProductDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.pagination.ProductPageResponse;
import com.vendavaultecommerceproject.response.product.ProductServerListResponse;
import com.vendavaultecommerceproject.response.product.ProductServerResponse;
import com.vendavaultecommerceproject.service.main.products.image.ProductImageService;
import com.vendavaultecommerceproject.service.main.products.product.ProductService;
import com.vendavaultecommerceproject.util.constants.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductController {

    private ProductService productService;
    private ProductImageService productImageService;

    public ProductController(ProductService productService, ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }

    @PostMapping("/product/upload")
    public ResponseEntity<CustomPaymentResponse> uploadProduct(@RequestParam("productImage")MultipartFile[] files,
                                                               @RequestPart("product")UploadProductDto uploadProductDto, HttpServletRequest request) throws Exception {
        return productService.uploadProduct(uploadProductDto,files,request);
    }

    @PostMapping("/product/image-upload")
    public ResponseEntity<Set<ProductImageEntity>> uploadProductImage(@RequestParam("productImage")MultipartFile[] files) throws Exception {
        return new ResponseEntity<>(productImageService.uploadProduct(files), HttpStatus.OK);
    }

    @GetMapping("/retrieved/{productImageId}")
    public ResponseEntity<Resource>downloadProductImage(@PathVariable("productImageId")String productImageId) throws Exception {
        ProductImageEntity imageEntity = productImageService.getProductImage(productImageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"image; fileName=\""+imageEntity.getFileName()
                +"\"")
                .body(new ByteArrayResource(imageEntity.getData()));
    }

    @PostMapping("products/admin/access")
    public ProductServerResponse approveOrRejectProduct(@RequestBody ApprovedOrRejectProductDto approvedOrRejectProductDto, HttpServletRequest request) throws ExecutionException, InterruptedException, DataNotFoundException {
        return  productService.approveOrRejectProductByAdmin(approvedOrRejectProductDto,request);
    }

    @PostMapping("/products/update")
    public ProductServerResponse updateProduct(@RequestBody UpdateProductDto updateProductDto,HttpServletRequest request){
        return productService.updateProduct(updateProductDto,request);
    }

    @PostMapping("/products/approved/for/seller")
    public ProductServerListResponse getAllProductForTheSeller(@RequestBody RetrieveUserDto retrieveUserDto,HttpServletRequest request){
        return productService.getAllApprovedProductForTheSeller(retrieveUserDto,request);
    }
    @PostMapping("/products/pending/for/seller")
    public ProductServerListResponse getAllPendingProductForTheSeller(@RequestBody RetrieveUserDto retrieveUserDto,HttpServletRequest request){
        return productService.getAllPendingProductForTheSeller(retrieveUserDto,request);
    }
    @PostMapping("/products/uploaded/for/seller")
    public ProductServerListResponse getAllUploadedProductForTheSeller(@RequestBody RetrieveUserDto retrieveUserDto,HttpServletRequest request){
        return productService.getAllUploadedProductForTheSeller(retrieveUserDto,request);
    }
    @PostMapping("/products/productsInStock/for/seller")
    public ProductServerListResponse getAllProductInStockForTheSeller(@RequestBody RetrieveUserDto retrieveUserDto,HttpServletRequest request){
        return productService.getAllProductInTheStockForTheSeller(retrieveUserDto,request);
    }

    @GetMapping("/products/available")
    public ResponseEntity<ProductPageResponse> getProductByPagination(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                      @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize){
        return ResponseEntity.ok(productService.getAllProductWithPagination(pageNumber,pageSize));
    }
    @GetMapping("/products/available/with/sorting")
    public ResponseEntity<ProductPageResponse> getProductByPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                      @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                                @RequestParam(defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                                                @RequestParam(defaultValue = AppConstants.SORT_ORDER,required = false)String sortDirection){
        return ResponseEntity.ok(productService.getAllProductWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection));
    }

    @GetMapping("/products/all/pending-with-sorting")
    public ResponseEntity<ProductPageResponse> getAllPendingProductWithPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                                @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                                @RequestParam(defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                                                @RequestParam(defaultValue = AppConstants.SORT_ORDER,required = false)String sortDirection){
        return ResponseEntity.ok(productService.getAllPendingProductWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection));
    }

    @GetMapping("/products/all/approved/for/seller-with-sorting")
    public ResponseEntity<ProductPageResponse> getAllApprovedProductForTheSellerWithPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                                            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                                            @RequestParam(defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                                                            @RequestParam(defaultValue = AppConstants.SORT_ORDER,required = false)String sortDirection,
                                                                                                         @RequestParam("sellerEmail")String sellerEmail) throws DataNotFoundException {
        return ResponseEntity.ok(productService.getAllApprovedProductForTheSellerWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection,sellerEmail));
    }

    @GetMapping("/products/all/pending/for/seller-with-sorting")
    public ResponseEntity<ProductPageResponse> getAllPendingProductForTheSellerWithPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                                                         @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                                                         @RequestParam(defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                                                                         @RequestParam(defaultValue = AppConstants.SORT_ORDER,required = false)String sortDirection,
                                                                                                         @RequestParam("sellerEmail")String sellerEmail) throws DataNotFoundException {
        return ResponseEntity.ok(productService.getAllPendingProductForTheSellerWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection,sellerEmail));
    }

    @GetMapping("/products/all/uploaded/for/seller-with-sorting")
    public ResponseEntity<ProductPageResponse> getAllUploadedProductForTheSellerWithPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                                                        @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                                                        @RequestParam(defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                                                                        @RequestParam(defaultValue = AppConstants.SORT_ORDER,required = false)String sortDirection,
                                                                                                        @RequestParam("sellerEmail")String sellerEmail) throws DataNotFoundException {
        return ResponseEntity.ok(productService.getAllUploadedProductForTheSellerWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDirection,sellerEmail));
    }
}
