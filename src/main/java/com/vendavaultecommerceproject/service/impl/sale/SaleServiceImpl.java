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
import com.vendavaultecommerceproject.model.email.EmailDetails;
import com.vendavaultecommerceproject.model.sales.SaleModel;
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
import com.vendavaultecommerceproject.util.constants.EmailTextConstants;
import com.vendavaultecommerceproject.utils.SaleModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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

    @Value("${baseUrl}")
    private String baseUrl;

    public SaleServiceImpl(SaleRepository saleRepository, ProductService productService, UserService userService, SellerService sellerService, CartRepository cartRepository, EmailService emailService, OrderTokenService orderTokenService, UserPayStackPaymentService userPayStackPaymentService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.userService = userService;
        this.sellerService = sellerService;
        this.cartRepository = cartRepository;
        this.emailService = emailService;
        this.orderTokenService = orderTokenService;
        this.userPayStackPaymentService = userPayStackPaymentService;
    }

    @Override
    public ResponseEntity<CustomPaymentResponse> saleProduct(SaleDto saleDto) {
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

            //send a delivery token to the buyer email address
            //to be provided during delivery for confirmation
//            EmailDetails buyerMail = EmailDetails.builder()
//                    .subject(EmailTextConstants.Subject)
//                    .messageBody(EmailTextConstants.message+orderToken.getToken()+EmailTextConstants.mailEnding)
//                    .recipient(buyer.getEmail())
//                    .build();
//            emailService.sendEmailAlert(buyerMail);


            //notify the seller that an order has been placed for delivery
//            EmailDetails sellerMail = EmailDetails.builder()
//                    .subject(EmailTextConstants.Subject)
//                    .messageBody(EmailTextConstants.sellerMessage+EmailTextConstants.mailEnding)
//                    .recipient(sale.getSeller().getEmail())
//                    .build();
//
//            emailService.sendEmailAlert(sellerMail);
            System.out.println("Order token is "+orderToken.getToken());
            return ResponseEntity.ok().body(new
                    CustomPaymentResponse(true,"Your order has been placed Successfully",null));
        }else {

            //proceed for an online payment
            sale.setSeller(getAllCartItemForTheBuyer(buyer).get(0).getSeller());
            saleRepository.save(sale);
            final OrderTokenEntity orderToken = new OrderTokenEntity(sale);
            orderTokenService.saveOrderToken(orderToken);

            updateCartItemCheckOutStatus(getAllCartItemForTheBuyer(buyer));

//            EmailDetails buyerMail = EmailDetails.builder()
//                    .subject(EmailTextConstants.Subject)
//                    .messageBody(EmailTextConstants.message+orderToken.getToken()+EmailTextConstants.mailEnding)
//                    .recipient(buyer.getEmail())
//                    .build();
//            emailService.sendEmailAlert(buyerMail);


            //notify the seller that an order has been placed for delivery
            EmailDetails sellerMail = EmailDetails.builder()
                    .subject(EmailTextConstants.Subject)
                    .messageBody(EmailTextConstants.sellerMessage+EmailTextConstants.mailEnding)
                    .recipient(sale.getSeller().getEmail())
                    .build();

            //emailService.sendEmailAlert(sellerMail);
            return userPayStackPaymentService.initializePayment(sale.getId());
            //return null;

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

    @Override
    public SaleListServerResponse getSaleForTheSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) {
        SellerEntity seller = sellerService.findSellerByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            return new SaleListServerResponse(baseUrl+request.getRequestURI(),"NOT Ok",
                    new SaleListResponse(406,"Sales Information","The provided user not found as a Seller",null));
        }
        return new SaleListServerResponse(baseUrl+request.getRequestURI(),"Ok",
                new SaleListResponse(200,"Sales Information","All your Sales",getSaleForTheSeller(seller)));
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
    private List<SaleModel> getSaleForTheSeller(SellerEntity seller){
        List<SaleEntity> saleEntities = saleRepository.findBySeller(seller);
        List<SaleModel> saleModels = new ArrayList<>();
        for (SaleEntity sale: saleEntities){
            saleModels.add(SaleModelUtil.getReturnedSaleModel(sale));
        }
        return saleModels;
    }

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
}
