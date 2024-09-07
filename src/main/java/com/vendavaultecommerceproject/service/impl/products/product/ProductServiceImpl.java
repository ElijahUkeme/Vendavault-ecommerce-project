package com.vendavaultecommerceproject.service.impl.products.product;


import com.vendavaultecommerceproject.dto.product.ApprovedOrRejectProductDto;
import com.vendavaultecommerceproject.dto.product.UpdateProductDto;
import com.vendavaultecommerceproject.dto.product.UploadProductDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.category.CategoryEntity;
import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.notification.dto.DevicesNotificationRequest;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.service.main.FCMService;
import com.vendavaultecommerceproject.notification.service.main.admin.AdminNotificationService;
import com.vendavaultecommerceproject.notification.service.main.seller.SellerNotificationService;
import com.vendavaultecommerceproject.payment.enums.PaymentStatus;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.service.seller.SellerPayStackService;
import com.vendavaultecommerceproject.repository.products.product.ProductRepository;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.response.pagination.ProductPageResponse;
import com.vendavaultecommerceproject.response.product.ProductListResponse;
import com.vendavaultecommerceproject.response.product.ProductResponse;
import com.vendavaultecommerceproject.response.product.ProductServerListResponse;
import com.vendavaultecommerceproject.response.product.ProductServerResponse;
import com.vendavaultecommerceproject.service.main.category.CategoryService;
import com.vendavaultecommerceproject.service.main.products.image.ProductImageService;
import com.vendavaultecommerceproject.service.main.products.product.ProductService;
import com.vendavaultecommerceproject.utils.ProductModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final SellerPayStackService sellerPayStackService;
    private final FCMService fcmService;
    private final SellerNotificationService sellerNotificationService;
    private final AdminNotificationService adminNotificationService;
    @Value("${baseUrl}")
    private String baseUrl;

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository, ProductImageService productImageService, CategoryService categoryService, SellerPayStackService sellerPayStackService, FCMService fcmService, SellerNotificationService sellerNotificationService, AdminNotificationService adminNotificationService) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.productImageService = productImageService;
        this.categoryService = categoryService;
        this.sellerPayStackService = sellerPayStackService;
        this.fcmService = fcmService;
        this.sellerNotificationService = sellerNotificationService;
        this.adminNotificationService = adminNotificationService;
    }

    @Override
    public ResponseEntity<CustomPaymentResponse> uploadProduct(UploadProductDto uploadProductDto, MultipartFile[] files, HttpServletRequest request) throws Exception {

        SellerEntity seller = sellerRepository.findByEmail(uploadProductDto.getSellerEMail());

        if (Objects.isNull(seller)) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Seller Email not found", null));
        }
        if (uploadProductDto.getPrice().intValue() <= 0) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Invalid product price", null));
        }
        categoryName(uploadProductDto.getCategory());

        Set<ProductImageEntity> productImageEntitySet = productImageService.uploadProduct(files);
        Thread.sleep(3000);

            ProductEntity product = ProductEntity.builder()
                    .productName(uploadProductDto.getProductName())
                    .brand(uploadProductDto.getBrand())
                    .category(uploadProductDto.getCategory())
                    .description(uploadProductDto.getDescription())
                    .productOwner(seller)
                    .price(uploadProductDto.getPrice())
                    .productImages(productImageEntitySet)
                    .status("Pending Approval")
                    .paymentStatus(PaymentStatus.PENDING.name())
                    .uploadedDate(LocalDate.now())
                    .uploadedTime(new Date())
                    .approvedOrRejectedDate(new Date())
                    .approvedOrRejectedDate(null)
                    .updatedDate(null)
                    .build();
            productRepository.save(product);
            System.out.println("The product Id is " + product.getId());
            notifyAdminOnProductUpload(product.getProductOwner().getUsername());
            return sellerPayStackService.initializePayment(product.getId());
    }
    @Override
    public ProductServerResponse approveOrRejectProductByAdmin(ApprovedOrRejectProductDto approvedOrRejectProductDto, HttpServletRequest request) throws ExecutionException, InterruptedException, DataNotFoundException {
        ProductEntity product = productRepository.findByProductId(approvedOrRejectProductDto.getProductId());
        if (Objects.isNull(product)) {
            return new ProductServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ProductResponse(406, "Product Update Info", "The product id not found", null));
        }
        product.setStatus(approvedOrRejectProductDto.getStatus());
        product.setApprovedOrRejectedDate(new Date());
        productRepository.save(product);
        notifySellerOnProductUpdateByAdminAndSave(product.getProductOwner().getEmail());
        //notifySellerOnProductUpdateByAdmin(product.getProductOwner().getFcmToken());

        return new ProductServerResponse(baseUrl + request.getRequestURI(), "OK", new ProductResponse(200, "Product Information", "Product updated Successfully",
                ProductModelUtil.getReturnedProductModel(product)));
    }
    @Override
    public ProductServerResponse updateProduct(UpdateProductDto updateProductDto, HttpServletRequest request) {
        ProductEntity product = productRepository.findByProductId(updateProductDto.getProductId());
        if (Objects.isNull(product)) {
            return new ProductServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ProductResponse(406, "Product Update Info", "Product id not found", null));
        }
        if (Objects.nonNull(updateProductDto.getProductName())) {
            product.setProductName(updateProductDto.getProductName());
        }
        if (Objects.nonNull(updateProductDto.getPrice())) {
            product.setPrice(updateProductDto.getPrice());
        }
        if (Objects.nonNull(updateProductDto.getBrand())) {
            product.setBrand(updateProductDto.getBrand());
        }
        if (Objects.nonNull(updateProductDto.getDescription())) {
            product.setDescription(updateProductDto.getDescription());
        }
        if (Objects.nonNull(updateProductDto.getCategory())) {
            product.setCategory(updateProductDto.getCategory());
        }
        product.setUpdatedDate(LocalDate.now());
        product.setUpdatedTime(new Date());
        productRepository.save(product);
        return new ProductServerResponse(baseUrl + request.getRequestURI(), "OK", new ProductResponse(200, "Product Information", "Product updated Successfully",
                ProductModelUtil.getReturnedProductModel(product)));
    }

//    @Override
//    public String updateProductAfterSale(UpdateProductAfterSaleDto updateProductAfterSaleDto, HttpServletRequest request) {
//        String status = "";
//        ProductEntity product = productRepository.findByProductId(updateProductAfterSaleDto.getProductId());
//        if (Objects.isNull(product)){
//            status = "Not found";
//        }
//        int availableQuantity = product.getQuantity();
//        int newQuantity = availableQuantity - updateProductAfterSaleDto.getQuantity();
//        if (newQuantity < 0){
//            status = "Not enough";
//        }else {
//            status = "success";
//            product.setQuantity(newQuantity);
//            productRepository.save(product);
//        }
//        return status;
   // }

    @Override
    public ProductPageResponse getAllProductWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductEntity> productPage = productRepository.findAll(pageable);

        //Only display product whose status is approved
        //And payment status is paid
        List<ProductModel> productModels = new ArrayList<>();
        for (ProductEntity product:productPage){
            if (product.getStatus().equalsIgnoreCase("Approved") && product.getPaymentStatus().equalsIgnoreCase("Paid")){
                productModels.add(ProductModelUtil.getReturnedProductModel(product));
            }
        }
        return new ProductPageResponse(
                productModels,
                pageNumber,
                pageSize,
                productPage.getTotalPages(),
                (int) productPage.getTotalElements(),
                productPage.isLast()
        );
    }

    @Override
    public ProductPageResponse getAllProductWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<ProductEntity> productPage = productRepository.findAll(pageable);

        //Only display product whose status is approved
        //And payment status is paid
        List<ProductModel> productModels = new ArrayList<>();
        for (ProductEntity product:productPage){
            if (product.getStatus().equalsIgnoreCase("Approved") && product.getPaymentStatus().equalsIgnoreCase("Paid")){
                productModels.add(ProductModelUtil.getReturnedProductModel(product));
            }
        }
        return new ProductPageResponse(
                productModels, pageNumber,
                pageSize, productPage.getTotalPages(),
                (int) productPage.getTotalElements(), productPage.isLast()
        );
    }

    @Override
    public ProductPageResponse getAllPendingProductWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<ProductEntity> productPage = productRepository.findAll(pageable);

        //Only display product whose status is pending
        //This is only for the admin
        //Admin will then decide either to approve the product or reject it
        List<ProductModel> productModels = new ArrayList<>();
        for (ProductEntity product:productPage){
            if (product.getStatus().equalsIgnoreCase("Pending")){
                productModels.add(ProductModelUtil.getReturnedProductModel(product));
            }
        }
        return new ProductPageResponse(
                productModels, pageNumber, pageSize, productPage.getTotalPages(), (int) productPage.getTotalElements(), productPage.isLast()
        );
    }

    @Override
    public ProductPageResponse getAllApprovedProductForTheSellerWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String sellerEmail) throws DataNotFoundException {
        SellerEntity seller = sellerRepository.findByEmail(sellerEmail);
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("Seller Email not found");
        }
        Sort sort = sortDirection.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<ProductEntity> productEntities = productRepository.getAllProductForTheSellerWithPagination(seller,pageable);
        List<ProductModel> approvedProduct = new ArrayList<>();
        for (ProductEntity product: productEntities){
            if (product.getStatus().equalsIgnoreCase("Approved")){
                approvedProduct.add(ProductModelUtil.getReturnedProductModel(product));
            }
        }
        return new ProductPageResponse(
                approvedProduct, pageNumber, pageSize, productEntities.getTotalPages(), (int) productEntities.getTotalElements(), productEntities.isLast()
        );
    }

    @Override
    public ProductPageResponse getAllPendingProductForTheSellerWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String sellerEmail) throws DataNotFoundException {
        SellerEntity seller = sellerRepository.findByEmail(sellerEmail);
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("Seller Email not found");
        }
        Sort sort = sortDirection.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<ProductEntity> productEntities = productRepository.getAllProductForTheSellerWithPagination(seller,pageable);
        List<ProductModel> approvedProduct = new ArrayList<>();
        for (ProductEntity product: productEntities){
            if (product.getStatus().equalsIgnoreCase("Pending")){
                approvedProduct.add(ProductModelUtil.getReturnedProductModel(product));
            }
        }
        return new ProductPageResponse(
                approvedProduct, pageNumber, pageSize, productEntities.getTotalPages(), (int) productEntities.getTotalElements(), productEntities.isLast()
        );
    }

    @Override
    public ProductPageResponse getAllUploadedProductForTheSellerWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String sellerEmail) throws DataNotFoundException {
        SellerEntity seller = sellerRepository.findByEmail(sellerEmail);
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("Seller Email not found");
        }
        Sort sort = sortDirection.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<ProductEntity> productEntities = productRepository.getAllProductForTheSellerWithPagination(seller,pageable);
        List<ProductModel> approvedProduct = new ArrayList<>();
        for (ProductEntity product: productEntities){
                approvedProduct.add(ProductModelUtil.getReturnedProductModel(product));

        }
        return new ProductPageResponse(
                approvedProduct, pageNumber, pageSize, productEntities.getTotalPages(), (int) productEntities.getTotalElements(), productEntities.isLast()
        );
    }

    @Override
    public ProductServerListResponse getAllApprovedProductForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) {
        SellerEntity seller = sellerRepository.findByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            return new ProductServerListResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new ProductListResponse(406,"Product Information","There is no seller with the provided Email",
                            null));
        }
        List<ProductModel> retrievedProduct = allProductsForTheSeller(seller);
        List<ProductModel> approvedProduct = new ArrayList<>();
        for (ProductModel productModel: retrievedProduct){
            if (productModel.getStatus().equalsIgnoreCase("Approved")&& productModel.getPaymentStatus().equalsIgnoreCase("Paid")){
                //only add product whose status is approved and payment status is paid to the list
                approvedProduct.add(productModel);
            }
        }
        return new ProductServerListResponse(baseUrl+request.getRequestURI(),"OK",
                new ProductListResponse(200,"Product Information","Product list retrieved Successfully",
                        approvedProduct));
    }

    @Override
    public ProductServerListResponse getAllPendingProductForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) {

        SellerEntity seller = sellerRepository.findByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            return new ProductServerListResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new ProductListResponse(406,"Product Information","There is no seller with the provided Email",
                            null));
        }
        List<ProductModel> retrievedProduct = allProductsForTheSeller(seller);
        List<ProductModel> pendingProduct = new ArrayList<>();
        for (ProductModel productModel: retrievedProduct){
            if (productModel.getStatus().equalsIgnoreCase("Pending Approval")){
                //only add product whose status is pending to the list
                pendingProduct.add(productModel);
            }
        }
        return new ProductServerListResponse(baseUrl+request.getRequestURI(),"OK",
                new ProductListResponse(200,"Product Information","Product list retrieved Successfully",
                        pendingProduct));
    }

    @Override
    public ProductServerListResponse getAllUploadedProductForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) {

        SellerEntity seller = sellerRepository.findByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            return new ProductServerListResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new ProductListResponse(406,"Product Information","There is no seller with the provided Email",
                            null));
        }
        return new ProductServerListResponse(baseUrl+request.getRequestURI(),"OK",
                new ProductListResponse(200,"Product Information","Product list retrieved Successfully",
                        allProductsForTheSeller(seller)));
    }

    @Override
    public ProductServerListResponse getAllProductInTheStockForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) {

        SellerEntity seller = sellerRepository.findByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            return new ProductServerListResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new ProductListResponse(406,"Product Information","There is no seller with the provided Email",
                            null));
        }
        List<ProductModel> retrievedProduct = allProductsForTheSeller(seller);
        List<ProductModel> productInStock = new ArrayList<>();
        for (ProductModel productModel: retrievedProduct){
            if (productModel.getStatus().equalsIgnoreCase("Approved")&& productModel.getPaymentStatus().equalsIgnoreCase("Paid")){
                //only add product whose status is approved and payment status is paid
                    productInStock.add(productModel);
            }
        }
        return new ProductServerListResponse(baseUrl+request.getRequestURI(),"OK",
                new ProductListResponse(200,"Product Information","Product list retrieved Successfully",
                        productInStock));
    }

    public List<ProductModel> allProductsForTheSeller(SellerEntity seller){
        List<ProductEntity> productEntityList = productRepository.findByProductOwner(seller);
        List<ProductModel> productModelList = new ArrayList<>();
        for (ProductEntity product: productEntityList){
            productModelList.add(ProductModelUtil.getReturnedProductModel(product));
        }
        return productModelList;

    }
    void categoryName(String categoryName) throws DataNotFoundException {
        String catName = "";
        List<CategoryEntity> categoryEntityList = categoryService.getAllCategory();
        boolean found = false;
        for (CategoryEntity category: categoryEntityList){
            if (categoryName.equalsIgnoreCase(category.getName())){
                catName = category.getName();
                found = true;
                break;
            }
        }
        if (!found){
            throw new DataNotFoundException("There is no category with this name");
        }
    }

    private void notifySellerOnProductUpdateByAdmin(String deviceToken) throws ExecutionException, InterruptedException {
        DevicesNotificationRequest devicesNotificationRequest = new DevicesNotificationRequest();
        devicesNotificationRequest.setDeviceToken(deviceToken);
        devicesNotificationRequest.setTitle("Product Update Notification");
        devicesNotificationRequest.setBody("Your pending uploaded product has been updated by the admin,\ncheck your products dashboard for the update");
        fcmService.sendNotificationToDevice(devicesNotificationRequest);
    }
    private void notifySellerOnProductUpdateByAdminAndSave(String sellerEmail) throws DataNotFoundException {
        SaveNotificationDto saveNotificationDto = SaveNotificationDto.builder()
                .title("Product upload Update")
                .message("Your product upload has been updated by the admin,check your profile for the new update")
                .email(sellerEmail)
                .build();
        sellerNotificationService.saveNotification(saveNotificationDto);
    }

    private void notifyAdminOnProductUpload(String sellerName) throws DataNotFoundException {
        SaveNotificationDto saveNotificationDto = SaveNotificationDto.builder()
                .title("Product upload Update")
                .message("A product has been uploaded by "+sellerName+",check the product dashboard for approval")
                .email("admin@gmail.com")
                .build();
        adminNotificationService.saveNotification(saveNotificationDto);
    }
}
