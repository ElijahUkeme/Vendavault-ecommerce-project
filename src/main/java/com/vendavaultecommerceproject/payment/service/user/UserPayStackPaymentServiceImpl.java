package com.vendavaultecommerceproject.payment.service.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.payment.enums.PaymentType;
import com.vendavaultecommerceproject.payment.enums.PaymentStatus;
import com.vendavaultecommerceproject.payment.entity.user.UserPaymentStack;
import com.vendavaultecommerceproject.payment.model.user.UserPaymentModel;
import com.vendavaultecommerceproject.payment.repository.user.UserPayStackPaymentRepository;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.user.UserResponse;
import com.vendavaultecommerceproject.payment.response.server.user.UserServerResponse;
import com.vendavaultecommerceproject.payment.service.template.RestTemplateService;
import com.vendavaultecommerceproject.payment.utils.UserPaymentModelUtils;
import com.vendavaultecommerceproject.repository.sale.SaleRepository;
import com.vendavaultecommerceproject.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.vendavaultecommerceproject.util.constants.ApiConstant.PAYSTACK_INITIALIZE_PAY;
import static com.vendavaultecommerceproject.util.constants.ApiConstant.PAYSTACK_VERIFY;

@Service
@RequiredArgsConstructor
public class UserPayStackPaymentServiceImpl implements UserPayStackPaymentService{

    @Value("${payStackSecretKey}")
    private String payStackSecretKey;

    @Value("${baseUrl}")
    private String baseUrl;
    private final RestTemplateService restTemplate;
    private final UserPayStackPaymentRepository userPayStackPaymentRepository;
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<CustomPaymentResponse> initializePayment(Long orderId) {

        Map<String, String> request = new HashMap<>();
        Optional<SaleEntity> order = saleRepository.findById(orderId);
        if (order.isEmpty()){
                return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"Order Id not found",null));

        }
        UserPaymentStack paymentStack = userPayStackPaymentRepository.findByOrder(order.get());
        if (Objects.nonNull(paymentStack)){
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"You have already initialized payment for this order",null));

        }

        double amountToBePaid = order.get().getTotalPrice() * 100;

        request.put("email", order.get().getBuyer().getEmail());
        request.put("amount", String.valueOf(amountToBePaid));

        ResponseEntity<CustomPaymentResponse> response = restTemplate.post(PAYSTACK_INITIALIZE_PAY, request, this.headers());
        System.out.println("The initialize payment response is "+response.getBody().toString());
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("The response is ok in this place "+response.getBody().getData().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapResponse = objectMapper.convertValue(response.getBody().getData(), Map.class);

            UserPaymentStack userPaymentStack = UserPaymentStack.builder()
                    .user(order.get().getBuyer())
                    .amount(order.get().getTotalPrice())
                    .status(PaymentStatus.PENDING.name())
                    .order(order.get())
                    .reference((String) mapResponse.get("reference"))
                    .accessCode((String) mapResponse.get("access_code"))
                    .build();
            userPayStackPaymentRepository.save(userPaymentStack);

            return ResponseEntity.ok( CustomPaymentResponse.builder()
                    .message(response.getBody().getMessage())
                    .status(true)
                    .data(mapResponse)
                    .build());
        }
        return ResponseEntity.badRequest().body(new  CustomPaymentResponse(false,response.getBody().getMessage(),null));
    }

    @Override
    public ResponseEntity<CustomPaymentResponse> verifyPayment(Long orderId) {
        Optional<SaleEntity> order = saleRepository.findById(orderId);
        if (order.isEmpty()){
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"Order Id not found",null));
        }
        SaleEntity sale = order.get();
        if (sale.getPaymentStatus().equalsIgnoreCase(PaymentStatus.PAID.name())){
            return ResponseEntity.accepted().body(new CustomPaymentResponse(true,"This payment has already been verified",null));
        }
        UserPaymentStack paymentStack = userPayStackPaymentRepository.findByOrder(sale);
        if (Objects.isNull(paymentStack)){
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"No Payment associated to this Order",null));
        }

        ResponseEntity<CustomPaymentResponse> response = restTemplate.get(PAYSTACK_VERIFY+paymentStack.getReference(), this.headers());
        if (response.getStatusCode()==HttpStatus.OK){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapResponse = objectMapper.convertValue(response.getBody().getData(), Map.class);
            int amount = (Integer) mapResponse.get("amount");
            paymentStack.setAmount(amount);
            paymentStack.setChannel((String) mapResponse.get("channel"));
            paymentStack.setCurrency((String) mapResponse.get("currency"));
            paymentStack.setPaidAt((String) mapResponse.get("paid_at"));
            paymentStack.setCreatedAt((String) mapResponse.get("created_at"));
            paymentStack.setGatewayResponse((String) mapResponse.get("gateway_response"));
            paymentStack.setIpAddress((String) mapResponse.get("ip_address"));
            paymentStack.setStatus((String) mapResponse.get("status"));
            userPayStackPaymentRepository.save(paymentStack);
            String paymentStatus = (String) mapResponse.get("status");
            System.out.println("The payment status is "+paymentStatus);
            if (paymentStatus.equalsIgnoreCase("success")){
                order.get().setPaymentStatus(PaymentStatus.PAID.name());
                order.get().setPaymentType(PaymentType.CARD.name());
            }
            saleRepository.save(order.get());
            return ResponseEntity.ok( CustomPaymentResponse.builder()
                    .message(response.getBody().getMessage())
                    .status(response.getBody().getStatus())
                    .data(mapResponse)
                    .build());
        }

        return ResponseEntity.badRequest().body(new  CustomPaymentResponse(false,response.getBody().getMessage(),null));
    }

    @Override
    public UserServerResponse getAllPayments(HttpServletRequest request) {
        List<UserPaymentStack> userPaymentStacks = userPayStackPaymentRepository.findAll();
        List<UserPaymentModel> userPaymentModels = new ArrayList<>();
        for (UserPaymentStack userPaymentStack: userPaymentStacks){
            userPaymentModels.add(UserPaymentModelUtils.getReturnedUserPaymentModel(userPaymentStack));
        }
        return new UserServerResponse(baseUrl+request.getRequestURI(),"OK",
                new UserResponse(200,"Payment List","All Purchased payment",userPaymentModels));
    }

    @Override
    public UserServerResponse getAllPaymentsForUser(HttpServletRequest request, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail);
        if (Objects.isNull(user)){
            return new UserServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new UserResponse(409,"User Authentication","No User with the provided email",null));
        }
        List<UserPaymentStack> userPaymentStacks = userPayStackPaymentRepository.findByUser(user);
        List<UserPaymentModel> userPaymentModels = new ArrayList<>();
        for (UserPaymentStack userPaymentStack: userPaymentStacks){
            userPaymentModels.add(UserPaymentModelUtils.getReturnedUserPaymentModel(userPaymentStack));
        }
        return new UserServerResponse(baseUrl+request.getRequestURI(),"OK",
                new UserResponse(200,"Payment List","All your payments",userPaymentModels));
    }

    private HttpHeaders headers() {
        System.out.println("The key is "+payStackSecretKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+payStackSecretKey);
        headers.set("Content-type", "application/json");

        return headers;
    }
}
