package com.vendavaultecommerceproject.service.impl.sale;

import com.vendavaultecommerceproject.dto.sale.SaleDto;
import com.vendavaultecommerceproject.dto.sale.UpdateSaleDto;
import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.entities.cart.CartItemEntity;
import com.vendavaultecommerceproject.entities.order.OrderTokenEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.sales.SaleModel;
import com.vendavaultecommerceproject.notification.dto.DevicesNotificationRequest;
import com.vendavaultecommerceproject.notification.dto.SaveNotificationDto;
import com.vendavaultecommerceproject.notification.model.NotificationModel;
import com.vendavaultecommerceproject.notification.service.main.FCMService;
import com.vendavaultecommerceproject.notification.service.main.admin.AdminNotificationService;
import com.vendavaultecommerceproject.notification.service.main.seller.SellerNotificationService;
import com.vendavaultecommerceproject.notification.service.main.user.UserNotificationService;
import com.vendavaultecommerceproject.payment.enums.PaymentStatus;
import com.vendavaultecommerceproject.payment.enums.PaymentType;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.service.user.UserPayStackPaymentService;
import com.vendavaultecommerceproject.repository.cart.CartRepository;
import com.vendavaultecommerceproject.repository.sale.SaleRepository;
import com.vendavaultecommerceproject.response.sale.SaleListResponse;
import com.vendavaultecommerceproject.response.sale.SaleListServerResponse;
import com.vendavaultecommerceproject.response.sale.SaleResponse;
import com.vendavaultecommerceproject.response.sale.SaleServerResponse;
import com.vendavaultecommerceproject.service.main.email.EmailService;
import com.vendavaultecommerceproject.service.main.order.OrderTokenService;
import com.vendavaultecommerceproject.service.main.products.product.ProductService;
import com.vendavaultecommerceproject.service.main.sale.SaleService;
import com.vendavaultecommerceproject.service.main.seller.SellerService;
import com.vendavaultecommerceproject.service.main.user.UserService;
import com.vendavaultecommerceproject.utils.SaleModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final UserService userService;

    private final SellerService sellerService;
    private final CartRepository cartRepository;
    private final EmailService emailService;
    private final OrderTokenService orderTokenService;
    private final UserPayStackPaymentService userPayStackPaymentService;
    private final FCMService fcmService;
    private final UserNotificationService userNotificationService;
    private final SellerNotificationService sellerNotificationService;
    private final AdminNotificationService adminNotificationService;

    @Value("${baseUrl}")
    private String baseUrl;

    public SaleServiceImpl(SaleRepository saleRepository, ProductService productService, UserService userService, SellerService sellerService, CartRepository cartRepository, EmailService emailService, OrderTokenService orderTokenService, UserPayStackPaymentService userPayStackPaymentService, FCMService fcmService, UserNotificationService userNotificationService, SellerNotificationService sellerNotificationService, AdminNotificationService adminNotificationService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.userService = userService;
        this.sellerService = sellerService;
        this.cartRepository = cartRepository;
        this.emailService = emailService;
        this.orderTokenService = orderTokenService;
        this.userPayStackPaymentService = userPayStackPaymentService;
        this.fcmService = fcmService;
        this.userNotificationService = userNotificationService;
        this.sellerNotificationService = sellerNotificationService;
        this.adminNotificationService = adminNotificationService;
    }

    @Override
    public ResponseEntity<CustomPaymentResponse> saleProduct(SaleDto saleDto) throws DataNotFoundException, ExecutionException, InterruptedException {
        UserEntity buyer = userService.findUserByEmail(saleDto.getBuyerEmail());
        if (Objects.isNull(buyer)){
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"Buyer Email Address not found",null));
            }

        if (Objects.isNull(saleDto.getPaymentType())|| saleDto.getPaymentType().equalsIgnoreCase("")){
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"Please provide a payment method",null));
        }
        SaleEntity sale = SaleEntity.builder()
                .status("Pending Delivery")
                .paymentType(saleDto.getPaymentType())
                .datePurchased(new Date())
                .deliveredPersonName(saleDto.getDeliveredPersonName())
                .buyer(buyer)
                .paymentStatus("Pending")
                .deliveredAddress(saleDto.getDeliveredAddress())
                .deliveredPhone(saleDto.getDeliveredPhone())
                .totalPrice(getTotalAmount(buyer))
                .cartItemList(getAllCartItemForTheBuyer(buyer))
                .build();

        if (getAllCartItemForTheBuyer(buyer).size()<1){
            return ResponseEntity.badRequest().body(new
                    CustomPaymentResponse(false,"There is no item in your cart",null));
        }

        //this is if the user want to pay on delivery
        if (saleDto.getPaymentType().equalsIgnoreCase(PaymentType.CASH.name())){
            saleRepository.save(sale);
            final OrderTokenEntity orderToken = new OrderTokenEntity(sale);
            orderTokenService.saveOrderToken(orderToken);

            updateCartItemCheckOutStatus(getAllCartItemForTheBuyer(buyer));

            //Drop an order update to the owners of the products ordered
            getAllProductOwners(sale);
            //drop an order update to the admin as well
            notifyAdminOnOrderRequest(buyer.getUsername());
            //sendPushNotification(sale);
            //orderNotification(saleDto.getSellerEmail(),sale.getCartItemList(),saleDto, sale.getId(), sale.getPaymentStatus());

            //Remove the items from the cart
            clearCartItem(buyer);
            return ResponseEntity.ok().body(new
                    CustomPaymentResponse(true,"Your order has been placed Successfully",null));
        }else {

            //proceed for an online payment
            saleRepository.save(sale);
            final OrderTokenEntity orderToken = new OrderTokenEntity(sale);
            orderTokenService.saveOrderToken(orderToken);

            updateCartItemCheckOutStatus(getAllCartItemForTheBuyer(buyer));

            //Drop an order update to the owners of the products ordered
            getAllProductOwners(sale);
            //update the admin also for order placed
            notifyAdminOnOrderRequest(buyer.getUsername());
            //sendPushNotification(sale);
            //orderNotification(saleDto.getSellerEmail(),sale.getCartItemList(),saleDto, sale.getId(), sale.getPaymentStatus());

            //Remove the items from the cart
            clearCartItem(buyer);
            return userPayStackPaymentService.initializePayment(sale.getId());
        }
    }

    @Override
    public SaleServerResponse confirmOrder(UpdateSaleDto updateSaleDto, HttpServletRequest request) throws DataNotFoundException {
        if (Objects.isNull(orderTokenService.getOrderToken(updateSaleDto.getToken()))){
            return new SaleServerResponse(baseUrl+request.getRequestURI(),"NOT OK",new SaleResponse(
                    406,"Order Token Verification","The provided token not found",null
            ));
        }
        SaleEntity sale = orderTokenService.getOrderToken(updateSaleDto.getToken());
        sale.setStatus(updateSaleDto.getStatus());
        sale.setPaymentStatus(PaymentStatus.PAID.name());
        saleRepository.save(sale);

        return new SaleServerResponse(baseUrl+request.getRequestURI(),"OK",new SaleResponse(
                200,"Order Delivery Information","Your delivery order has been updated successful",null
        ));
    }

//    @Override
//    public SaleListServerResponse getSaleForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) {
//        SellerEntity seller = sellerService.findSellerByEmail(retrieveUserDto.getEmail());
//        if (Objects.isNull(seller)){
//            return new SaleListServerResponse(baseUrl+request.getRequestURI(),"NOT Ok",
//                    new SaleListResponse(406,"Sales Information","The provided user not found as a Seller",null));
//        }
//        return new SaleListServerResponse(baseUrl+request.getRequestURI(),"Ok",
//                new SaleListResponse(200,"Sales Information","All your Sales",getSaleForTheSeller(seller)));
//    }

    @Override
    public ResponseEntity<List<NotificationModel>> allOrdersForTheSeller(RetrieveUserDto userDto) throws DataNotFoundException {
        SellerEntity seller = sellerService.findSellerByEmail(userDto.getEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("There is no seller with this email");
        }
        List<NotificationModel> models = new ArrayList<>();
        List<SaleEntity> saleEntities = saleRepository.findAll();
        List<CartItemEntity> sellerOrderItems = new ArrayList<>();
        double amount = 0;
        for (SaleEntity sale:saleEntities){
            List<CartItemEntity> cartItemEntityList = sale.getCartItemList().stream().toList();
            for (CartItemEntity cartItemEntity : cartItemEntityList) {
                if (cartItemEntity.getProduct().getProductOwner().equals(seller)) {
                    sellerOrderItems.add(cartItemEntity);

                }
                    amount +=cartItemEntity.getTotalPrice();
                    NotificationModel notificationModel = NotificationModel.builder()
                            .orderId(sale.getId())
                            .deliveredPersonPhone(sale.getDeliveredPhone())
                            .orderedDate(sale.getDatePurchased())
                            .deliveredPersonAddress(sale.getDeliveredAddress())
                            .deliveredPersonName(sale.getDeliveredPersonName())
                            .totalAmount(amount)
                            .cartItems(sellerOrderItems)
                            .build();
                    models.add(notificationModel);
            }
        }
        return new ResponseEntity<>(models,HttpStatus.OK);
    }

    @Override
    public SaleListServerResponse getAllSales(HttpServletRequest request) {
        return new SaleListServerResponse(baseUrl+request.getRequestURI(),"OK",
                new SaleListResponse(200,"Sales Information","Sales Information Retrieved Successfully",getAllSales()));
    }
    private List<SaleModel> getAllSales(){
        List<SaleEntity> saleEntities = saleRepository.findAll();
        List<SaleModel> saleModels = new ArrayList<>();
        for (SaleEntity sale: saleEntities){
            saleModels.add(SaleModelUtil.getReturnedSaleModel(sale));
        }
        return saleModels;
    }
//    private List<SaleModel> getSaleForTheSeller(SellerEntity seller){
//        List<SaleEntity> saleEntities = saleRepository.findBySeller(seller);
//        List<SaleModel> saleModels = new ArrayList<>();
//        for (SaleEntity sale: saleEntities){
//            saleModels.add(SaleModelUtil.getReturnedSaleModel(sale));
//        }
//        return saleModels;
//    }

    List<CartItemEntity> getAllCartItemForTheBuyer(UserEntity user){
        List<CartItemEntity> cartItemEntityList =  cartRepository.findByBuyer(user);
        List<CartItemEntity> cartList = new ArrayList<>();
        for (CartItemEntity cartItem: cartItemEntityList){
            if (!(cartItem.isCheckOut())){
                cartList.add(cartItem);
            }
        }
        return cartList;
    }
    private int getTotalAmount(UserEntity user){
        int total = 0;
        List<CartItemEntity> cartItemEntityList = cartRepository.findByBuyer(user);
        for (CartItemEntity cartItem : cartItemEntityList) {

            //users has so many products in the cart already
            //so will only add those that has not been
            //checkedOut yet
            if (!(cartItem.isCheckOut())){
                total += cartItem.getTotalPrice();
            }
        }
        return total;
    }
    void updateCartItemCheckOutStatus(List<CartItemEntity> cartItemEntityList){
        for (CartItemEntity cartItem: cartItemEntityList){
            cartItem.setCheckOut(true);
            cartRepository.save(cartItem);
        }
    }
    private ResponseEntity<NotificationModel> orderNotification(String sellerEmail,List<CartItemEntity>cartItems,SaleDto saleDto,Long orderId,String paymentStatus) throws DataNotFoundException {
        SellerEntity seller = sellerService.findSellerByEmail(sellerEmail);
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("Seller Email not found");
        }
        double totalAmount = 0;
        List<CartItemEntity> cartItemEntityList = new ArrayList<>();
        for (CartItemEntity cartItem:cartItems){

            if (cartItem.getProduct().getProductOwner()==seller){
                totalAmount +=cartItem.getTotalPrice();
                cartItemEntityList.add(cartItem);
            }
        }
        NotificationModel notificationModel = NotificationModel.builder()
                .orderId(orderId)
                .orderedDate(new Date())
                .deliveredPersonAddress(saleDto.getDeliveredAddress())
                .deliveredPersonPhone(saleDto.getDeliveredPhone())
                .deliveredPersonName(saleDto.getDeliveredPersonName())
                .paymentStatus(paymentStatus)
                .cartItems(cartItemEntityList)
                .totalAmount(totalAmount)
                .build();

        return new ResponseEntity<>(notificationModel, HttpStatus.OK);
    }

    private void clearCartItem(UserEntity user){
        for (CartItemEntity cartItem: getAllCartItemForTheBuyer(user)){
            cartRepository.delete(cartItem);
        }
    }
    private void sendPushNotification(SaleEntity sale) throws ExecutionException, InterruptedException {
        List<CartItemEntity> orderItems = sale.getCartItemList();
        List<SellerEntity> sellerEntities = new ArrayList<>();
        for(CartItemEntity cartItem: orderItems){
            sellerEntities.add(cartItem.getSeller());
        }
        for (SellerEntity seller: sellerEntities){
            notifySellers(seller.getFcmToken());
        }
    }
    private void notifySellers(String deviceToken) throws ExecutionException, InterruptedException {
        DevicesNotificationRequest devicesNotificationRequest = new DevicesNotificationRequest();
        devicesNotificationRequest.setDeviceToken(deviceToken);
        devicesNotificationRequest.setTitle("Order Notification");
        devicesNotificationRequest.setBody("An order has been placed for delivery,\ncheck your order dashboard for the items");
        fcmService.sendNotificationToDevice(devicesNotificationRequest);
    }
    private void notifySellersOnOrderPlaced(String sellerEmail,String buyerName) throws DataNotFoundException {
        SaveNotificationDto saveNotificationDto = SaveNotificationDto.builder()
                .title("Product Order Notification")
                .message(buyerName+" placed an order for your delivery,check your page for it")
                .email(sellerEmail)
                .build();
        sellerNotificationService.saveNotification(saveNotificationDto);
    }
    private void getAllProductOwners(SaleEntity sale) throws DataNotFoundException {
        List<CartItemEntity> cartItemEntityList = sale.getCartItemList().stream().toList();
        for (CartItemEntity cartItemEntity : cartItemEntityList) {
            notifySellersOnOrderPlaced(cartItemEntity.getSeller().getEmail(),sale.getBuyer().getUsername());
        }
    }

    private void notifyAdminOnOrderRequest(String buyerName) throws DataNotFoundException {
        SaveNotificationDto saveNotificationDto = SaveNotificationDto.builder()
                .title("Order request Update")
                .message("An order has been placed by "+buyerName+",check the order dashboard for follow up")
                .email("admin@gmail.com")
                .build();
        adminNotificationService.saveNotification(saveNotificationDto);
    }
}
