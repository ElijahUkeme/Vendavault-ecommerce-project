package com.vendavaultecommerceproject.payment.service.video;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendavaultecommerceproject.entities.product.entity.ProductEntity;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import com.vendavaultecommerceproject.payment.entity.seller.SellerPaymentStack;
import com.vendavaultecommerceproject.payment.entity.video.VideoUploadPaymentEntity;
import com.vendavaultecommerceproject.payment.enums.PaymentStatus;
import com.vendavaultecommerceproject.payment.model.seller.SellerPaymentModel;
import com.vendavaultecommerceproject.payment.model.video.VideoUploadPaymentModel;
import com.vendavaultecommerceproject.payment.repository.video.VideoUploadPaymentRepository;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.seller.SellerServerPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.video.VideoPaymentResponse;
import com.vendavaultecommerceproject.payment.response.server.video.VideoPaymentServerResponse;
import com.vendavaultecommerceproject.payment.service.template.RestTemplateService;
import com.vendavaultecommerceproject.payment.utils.SellerPaymentModelUtils;
import com.vendavaultecommerceproject.payment.utils.VideoPaymentModelUtils;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.repository.video.VideoRepository;
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
public class VideoPaymentServiceImpl implements VideoPaymentService{

    @Value("${baseUrl}")
    private String baseUrl;
    @Value("${payStackSecretKey}")
    private String payStackSecretKey;
    private final VideoUploadPaymentRepository videoUploadPaymentRepository;
    private final SellerRepository sellerRepository;
    private final RestTemplateService restTemplate;
    private final VideoRepository videoRepository;

    @Override
    public ResponseEntity<CustomPaymentResponse> initializePayment(Long videoId) {
        Map<String, String> request = new HashMap<>();
        Optional<VideoEntity> video = videoRepository.findById(videoId);
        if (video.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Video Id not found", null));
        }
        VideoUploadPaymentEntity videoUploadPayment = videoUploadPaymentRepository.findByVideo(video.get());
        if (Objects.nonNull(videoUploadPayment)) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "You have already initialized payment for this video", null));
        }
        double amountToBePaid = 1000 * 100;

        request.put("email", video.get().getSeller().getEmail());
        request.put("amount", String.valueOf(amountToBePaid));

        ResponseEntity<CustomPaymentResponse> response = restTemplate.post(PAYSTACK_INITIALIZE_PAY, request, this.headers());
        System.out.println("The initialize payment response is " + response.getBody().toString());
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("The response is ok in this place " + response.getBody().getData().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> mapResponse = objectMapper.convertValue(response.getBody().getData(), Map.class);

            VideoUploadPaymentEntity videoUploadPaymentEntity = VideoUploadPaymentEntity.builder()
                    .amount(amountToBePaid)
                    .status(PaymentStatus.PENDING.name())
                    .video(video.get())
                    .seller(video.get().getSeller())
                    .reference((String) mapResponse.get("reference"))
                    .accessCode((String) mapResponse.get("access_code"))
                    .build();
            videoUploadPaymentRepository.save(videoUploadPaymentEntity);
            return ResponseEntity.ok(CustomPaymentResponse.builder()
                    .message(response.getBody().getMessage())
                    .status(true)
                    .data(mapResponse)
                    .build());
        }
        return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, response.getBody().getMessage(), null));


        }

        @Override
        public ResponseEntity<CustomPaymentResponse> verifyPayment (Long videoId){

            Optional<VideoEntity> video = videoRepository.findById(videoId);
            if (video.isEmpty()) {
                return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Video Id not found", null));
            }
            VideoEntity retrievedVideo = video.get();
            if (retrievedVideo.getPaymentStatus().equalsIgnoreCase(PaymentStatus.PAID.name())){
                return ResponseEntity.accepted().body(new CustomPaymentResponse(true,"This payment has already been verified",null));
            }
            VideoUploadPaymentEntity videoUploadPayment = videoUploadPaymentRepository.findByVideo(retrievedVideo);
            if (Objects.isNull(videoUploadPayment)){
                return ResponseEntity.badRequest().body(new CustomPaymentResponse(false,"No Payment associated to this Video",null));
            }

            ResponseEntity<CustomPaymentResponse> response = restTemplate.get(PAYSTACK_VERIFY+videoUploadPayment.getReference(), this.headers());
            if (response.getStatusCode()==HttpStatus.OK){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> mapResponse = objectMapper.convertValue(response.getBody().getData(), Map.class);
                int amount = (Integer) mapResponse.get("amount");
                videoUploadPayment.setAmount(amount);
                videoUploadPayment.setSeller(retrievedVideo.getSeller());
                videoUploadPayment.setChannel((String) mapResponse.get("channel"));
                videoUploadPayment.setCurrency((String) mapResponse.get("currency"));
                videoUploadPayment.setPaidAt((String) mapResponse.get("paid_at"));
                videoUploadPayment.setCreatedAt((String) mapResponse.get("created_at"));
                videoUploadPayment.setGatewayResponse((String) mapResponse.get("gateway_response"));
                videoUploadPayment.setIpAddress((String) mapResponse.get("ip_address"));
                videoUploadPayment.setStatus((String) mapResponse.get("status"));
                videoUploadPaymentRepository.save(videoUploadPayment);
                String paymentStatus = (String) mapResponse.get("status");
                System.out.println("The payment status is "+paymentStatus);
                if (paymentStatus.equalsIgnoreCase("success")){
                    retrievedVideo.setPaymentStatus(PaymentStatus.PAID.name());
                }
                videoRepository.save(retrievedVideo);
                return ResponseEntity.ok( CustomPaymentResponse.builder()
                        .message(response.getBody().getMessage())
                        .status(response.getBody().getStatus())
                        .data(mapResponse)
                        .build());
            }

            return ResponseEntity.badRequest().body(new  CustomPaymentResponse(false,response.getBody().getMessage(),null));

        }


    @Override
        public VideoPaymentServerResponse getAllPayment (HttpServletRequest request){
        List<VideoUploadPaymentEntity> videoUploadPaymentEntities = videoUploadPaymentRepository.findAll();
        List<VideoUploadPaymentModel> videoUploadPaymentModels = new ArrayList<>();
        for (VideoUploadPaymentEntity videoUploadPaymentEntity: videoUploadPaymentEntities){
            videoUploadPaymentModels.add(VideoPaymentModelUtils.getReturnedVideoPaymentUtil(videoUploadPaymentEntity));
        }
        return new VideoPaymentServerResponse(baseUrl+request.getRequestURI(),"OK",
                new VideoPaymentResponse(200,"Seller Video Payment List","List Retrieved Successfully",videoUploadPaymentModels));
        }

        @Override
        public VideoPaymentServerResponse getAllPaymentForTheSeller (HttpServletRequest request, String sellerEmail){

            SellerEntity seller = sellerRepository.findByEmail(sellerEmail);
            if (Objects.isNull(seller)){
                return new VideoPaymentServerResponse(baseUrl+request.getRequestURI(),"NOT OK",
                        new VideoPaymentResponse(200,"Seller Authentication","Seller email not found",null));
            }
            List<VideoUploadPaymentEntity> videoUploadPaymentEntities = videoUploadPaymentRepository.findBySeller(seller);

            List<VideoUploadPaymentModel> videoUploadPaymentModels = new ArrayList<>();
            for (VideoUploadPaymentEntity videoUploadPayment: videoUploadPaymentEntities){
                videoUploadPaymentModels.add(VideoPaymentModelUtils.getReturnedVideoPaymentUtil(videoUploadPayment));
            }
            return new VideoPaymentServerResponse(baseUrl+request.getRequestURI(),"OK",
                    new VideoPaymentResponse(200,"Seller Video Payment List","All your Video Payments",videoUploadPaymentModels));
        }

    private HttpHeaders headers() {
        System.out.println("The key is "+payStackSecretKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+payStackSecretKey);
        headers.set("Content-type", "application/json");

        return headers;
    }
}
