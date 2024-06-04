package com.vendavaultecommerceproject.service.impl.video;

import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.dto.video.AdminApproveOrRejectVideoDto;
import com.vendavaultecommerceproject.dto.video.VideoDto;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.model.video.VideoModel;
import com.vendavaultecommerceproject.payment.enums.PaymentStatus;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.payment.service.video.VideoPaymentService;
import com.vendavaultecommerceproject.repository.seller.SellerRepository;
import com.vendavaultecommerceproject.repository.video.VideoRepository;
import com.vendavaultecommerceproject.response.video.VideoListResponse;
import com.vendavaultecommerceproject.response.video.VideoResponse;
import com.vendavaultecommerceproject.response.video.VideoServerListResponse;
import com.vendavaultecommerceproject.response.video.VideoServerResponse;
import com.vendavaultecommerceproject.service.main.video.VideoAttachmentService;
import com.vendavaultecommerceproject.service.main.video.VideoService;
import com.vendavaultecommerceproject.util.constants.ApiConstant;
import com.vendavaultecommerceproject.util.constants.AppStrings;
import com.vendavaultecommerceproject.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoAttachmentService videoAttachmentService;
    private final VideoPaymentService videoPaymentService;
    private final SellerRepository sellerRepository;
    @Value("${baseUrl}")
    private String baseUrl;

    public VideoServiceImpl(VideoRepository videoRepository, VideoAttachmentService videoAttachmentService, VideoPaymentService videoPaymentService, SellerRepository sellerRepository) {
        this.videoRepository = videoRepository;
        this.videoAttachmentService = videoAttachmentService;
        this.videoPaymentService = videoPaymentService;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ResponseEntity<CustomPaymentResponse> uploadVideo(MultipartFile file, VideoDto videoDto, HttpServletRequest request) throws DataNotAcceptableException, IOException {
        VideoAttachmentEntity videoAttachment = videoAttachmentService.saveVideoAttachment(file);
        SellerEntity seller = sellerRepository.findByEmail(videoDto.getSellerEmail());

        if (Objects.isNull(seller)) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Seller Email not found", null));
        }

        if (Objects.isNull(videoAttachment)) {
            return ResponseEntity.badRequest().body(new CustomPaymentResponse(false, "Invalid file format", null));
        }
        String videoUrl = "video/" + videoAttachment.getId();
        VideoEntity video = VideoEntity.builder()
                .videoUrl(videoUrl)
                .title(videoDto.getTitle())
                .status("Pending Approval")
                .paymentStatus(PaymentStatus.PENDING.name())
                .seller(seller)
                .build();
        videoRepository.save(video);
        System.out.println("The video Id is "+video.getId());
        return videoPaymentService.initializePayment(video.getId());

    }

    @Override
    public VideoServerResponse approveAVideo(AdminApproveOrRejectVideoDto approveOrRejectVideoDto, HttpServletRequest request) throws DataNotFoundException {
        Optional<VideoEntity> video = videoRepository.findById(approveOrRejectVideoDto.getVideoId());
        if (video.isEmpty()){
            throw new DataNotFoundException("Video Id not found");
        }
        video.get().setStatus(approveOrRejectVideoDto.getStatus());
        video.get().setApprovedDate(new Date());
        videoRepository.save(video.get());
        return new VideoServerResponse(baseUrl+request.getRequestURI(), AppStrings.statusOk,
                new VideoResponse(ApiConstant.STATUS_CODE_OK,"Video Approval","Video Approved Successfully",
                        VideoModelUtil.getReturnedVideoModel(video.get())));
    }

    @Override
    public VideoServerResponse getAVideo(Long videoId, HttpServletRequest request) throws DataNotFoundException {
        Optional<VideoEntity> video = videoRepository.findById(videoId);
        if (video.isEmpty()){
            throw new DataNotFoundException("Video Id not found");
        }

        return new VideoServerResponse(baseUrl+request.getRequestURI(), AppStrings.statusOk,
                new VideoResponse(ApiConstant.STATUS_CODE_OK,"Video Retrieval","Video Retrieved Successfully",
                        VideoModelUtil.getReturnedVideoModel(video.get())));
    }

    @Override
    public VideoServerListResponse getAllVideoForSeller(RetrieveUserDto retrieveUserDto, HttpServletRequest request) throws DataNotFoundException {
        SellerEntity seller = sellerRepository.findByEmail(retrieveUserDto.getEmail());
        if (Objects.isNull(seller)){
            throw new DataNotFoundException("Seller not found with the provided email");
        }
        List<VideoModel> retrievedVideo = allVideosForTheSeller(seller);
        List<VideoModel> videoModels = new ArrayList<>();
        for (VideoModel videoModel: retrievedVideo){
                videoModels.add(videoModel);
        }
        return new VideoServerListResponse(baseUrl+request.getRequestURI(),AppStrings.statusOk,
                new VideoListResponse(ApiConstant.STATUS_CODE_OK,"Video List","All your Videos",
                        videoModels));
    }

    @Override
    public VideoServerListResponse getAllVideos(HttpServletRequest request) {
        List<VideoModel> videoModels = new ArrayList<>();
        List<VideoEntity> videoEntities = videoRepository.findAll();
        for (VideoEntity video: videoEntities){
            videoModels.add(VideoModelUtil.getReturnedVideoModel(video));
        }
        return new VideoServerListResponse(baseUrl+request.getRequestURI(),AppStrings.statusOk,
                new VideoListResponse(ApiConstant.STATUS_CODE_OK,"Video List","All videos",videoModels));
    }
    @Override
    public VideoServerListResponse getAllApprovedVideo(HttpServletRequest request) {
        List<VideoModel> videoModels = new ArrayList<>();
        List<VideoEntity> videoEntities = videoRepository.findAll();
        for (VideoEntity video: videoEntities){
            if (video.getStatus().equalsIgnoreCase("Approved")&& video.getPaymentStatus().equalsIgnoreCase("Paid")){
                videoModels.add(VideoModelUtil.getReturnedVideoModel(video));
            }
        }
        return new VideoServerListResponse(baseUrl+request.getRequestURI(),AppStrings.statusOk,
                new VideoListResponse(ApiConstant.STATUS_CODE_OK,"Video List","All approved videos",videoModels));
    }

    public List<VideoModel> allVideosForTheSeller(SellerEntity seller){
        List<VideoEntity> videoEntities = videoRepository.getAllVideosForSeller(seller);
        List<VideoModel> videoModels = new ArrayList<>();
        for (VideoEntity video: videoEntities){
            videoModels.add(VideoModelUtil.getReturnedVideoModel(video));
        }
        return videoModels;

    }
}
