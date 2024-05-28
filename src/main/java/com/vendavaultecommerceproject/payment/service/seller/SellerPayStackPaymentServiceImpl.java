package com.vendavaultecommerceproject.payment.service.seller;

import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.sale.SaleEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.payment.entity.seller.SellerPaymentStack;
import com.vendavaultecommerceproject.payment.entity.user.UserPaymentStack;
import com.vendavaultecommerceproject.payment.enums.PaymentStatus;
import com.vendavaultecommerceproject.payment.enums.PaymentType;
import com.vendavaultecommerceproject.payment.model.seller.SellerPaymentModel;
import com.vendavaultecommerceproject.payment.repository.seller.SellerPayStackPaymentRepository;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerServerPaymentResponse;
import com.vendavaultecommerceproject.payment.service.template.RestTemplateService;
import com.vendavaultecommerceproject.payment.utils.SellerPaymentModelUtils;
import com.vendavaultecommerceproject.repository.products.product.ProductRepository;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import static com.vendavaultecommerceproject.util.constants.ApiConstant.*;


@Service
@RequiredArgsConstructor
public class SellerPayStackPaymentServiceImpl implements SellerPayStackService {



    @Value("${baseUrl}")
    private String baseUrl;
    @Value("${payStackSecretKey}")
    private String payStackSecretKey;
    private final SellerPayStackPaymentRepository sellerPayStackPaymentRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final RestTemplateService restTemplate;

    @Override
    public ResponseEntity<CustomPaymentResponse> initializePayment(Long productId) {

        Map<String, String> request = new HashMap<>();
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Product Id not found", null));
        }

        SellerPaymentStack paymentStack = sellerPayStackPaymentRepository.findByProduct(product.get());
        if (Objects.nonNull(paymentStack)) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "You have already initialized payment for this product", null));
        }
        double amountToBePaid = 1000 * 100;

        request.put("email", product.get().getProductOwner().getEmail());
        request.put("amount", String.valueOf(amountToBePaid));

        ResponseEntity<CustomPaymentResponse> response = restTemplate.post(PAYSTACK_INITIALIZE_PAY, request, this.headers());
        System.out.println("The initialize payment response is " + response.getBody().toString());
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("The response is ok in this place " + response.getBody().getData().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapResponse = objectMapper.convertValue(response.getBody().getData(), Map.class);

            SellerPaymentStack sellerPaymentStack = SellerPaymentStack.builder()
                    .amount(amountToBePaid)
                    .status(PaymentStatus.REFUNDED.name())
                    .product(product.get())
                    .seller(product.get().getProductOwner())
                    .reference((String) mapResponse.get("reference"))
                    .accessCode((String) mapResponse.get("access_code"))
                    .build();

            sellerPayStackPaymentRepository.save(sellerPaymentStack);
            return ResponseEntity.ok(CustomPaymentResponse.builder()
                    .message(response.getBody().getMessage())
                    .status(true)
                    .data(mapResponse)
                    .build());
        }
        return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, response.getBody().getMessage(), null));
    }

    @Override
    public ResponseEntity<CustomPaymentResponse> verifyPayment(Long productId) {
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Product Id not found", null));
        }
        ProductEntity retrievedProduct = product.get();
        if (retrievedProduct.getPaymentStatus().equalsIgnoreCase(PaymentStatus.PAID.name())){
            return ResponseEntity.accepted().body(new CustomPaymentResponse(true,"This payment has already been verified",null));
        }
        SellerPaymentStack sellerPaymentStack = sellerPayStackPaymentRepository.findByProduct(retrievedProduct);
        if (Objects.isNull(sellerPaymentStack)){
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"No Payment associated to this product",null));
        }

        ResponseEntity<CustomPaymentResponse> response = restTemplate.get(PAYSTACK_VERIFY+sellerPaymentStack.getReference(), this.headers());
        if (response.getStatusCode()==HttpStatus.OK){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapResponse = objectMapper.convertValue(response.getBody().getData(), Map.class);
            int amount = (Integer) mapResponse.get("amount");
            sellerPaymentStack.setAmount(amount);
            sellerPaymentStack.setSeller(retrievedProduct.getProductOwner());
            sellerPaymentStack.setChannel((String) mapResponse.get("channel"));
            sellerPaymentStack.setCurrency((String) mapResponse.get("currency"));
            sellerPaymentStack.setPaidAt((String) mapResponse.get("paid_at"));
            sellerPaymentStack.setCreatedAt((String) mapResponse.get("created_at"));
            sellerPaymentStack.setGatewayResponse((String) mapResponse.get("gateway_response"));
            sellerPaymentStack.setIpAddress((String) mapResponse.get("ip_address"));
            sellerPaymentStack.setStatus((String) mapResponse.get("status"));
            sellerPayStackPaymentRepository.save(sellerPaymentStack);
            String paymentStatus = (String) mapResponse.get("status");
            System.out.println("The payment status is "+paymentStatus);
            if (paymentStatus.equalsIgnoreCase("success")){
                retrievedProduct.setPaymentStatus(PaymentStatus.PAID.name());
            }
            productRepository.save(retrievedProduct);
            return ResponseEntity.ok( CustomPaymentResponse.builder()
                    .message(response.getBody().getMessage())
                    .status(response.getBody().getStatus())
                    .data(mapResponse)
                    .build());
        }

        return ResponseEntity.badRequest().body(new  CustomPaymentResponse(false,response.getBody().getMessage(),null));

}

    @Override
    public SellerServerPaymentResponse getAllPayment(HttpServletRequest request) {
        List<SellerPaymentStack> sellerPaymentStacks = sellerPayStackPaymentRepository.findAll();
        List<SellerPaymentModel> sellerPaymentModels = new ArrayList<>();
        for (SellerPaymentStack sellerPaymentStack: sellerPaymentStacks){
            sellerPaymentModels.add(SellerPaymentModelUtils.getReturnedSellerPaymentUtil(sellerPaymentStack));
        }
        return new SellerServerPaymentResponse(baseUrl+request.getRequestURI(),"OK",
                new SellerPaymentResponse(200,"Seller Payment List","List Retrieved Successfully",sellerPaymentModels));
    }

    @Override
    public SellerServerPaymentResponse getAllPaymentForTheSeller(HttpServletRequest request, String sellerEmail) {
        SellerEntity seller = sellerRepository.findByEmail(sellerEmail);
        if (Objects.isNull(seller)){
            return new SellerServerPaymentResponse(baseUrl+request.getRequestURI(),"NOT OK",
                    new SellerPaymentResponse(200,"Seller Authentication","Seller email not found",null));
        }
        List<SellerPaymentStack> sellerPaymentStacks = sellerPayStackPaymentRepository.findBySeller(seller);

        List<SellerPaymentModel> sellerPaymentModels = new ArrayList<>();
        for (SellerPaymentStack sellerPaymentStack: sellerPaymentStacks){
            sellerPaymentModels.add(SellerPaymentModelUtils.getReturnedSellerPaymentUtil(sellerPaymentStack));
        }
        return new SellerServerPaymentResponse(baseUrl+request.getRequestURI(),"OK",
                new SellerPaymentResponse(200,"Seller Payment List","All your Payments",sellerPaymentModels));
    }

    private HttpHeaders headers() {
        System.out.println("The key is "+payStackSecretKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+payStackSecretKey);
        headers.set("Content-type", "application/json");

        return headers;
    }
}