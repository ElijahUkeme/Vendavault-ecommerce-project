package com.vendavaultecommerceproject.service.impl.video;

import com.vendavaultecommerceproject.dto.video.VideoDto;
import com.vendavaultecommerceproject.entities.seller.SellerEntity;
import com.vendavaultecommerceproject.entities.user.UserEntity;
import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.entities.video.VideoEntity;
import com.vendavaultecommerceproject.repository.video.VideoRepository;
import com.vendavaultecommerceproject.response.product.ProductResponse;
import com.vendavaultecommerceproject.response.product.ProductServerResponse;
import com.vendavaultecommerceproject.response.user.ApiResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.service.main.video.VideoAttachmentService;
import com.vendavaultecommerceproject.service.main.video.VideoService;
import com.vendavaultecommerceproject.utils.SellerModelUtil;
import com.vendavaultecommerceproject.utils.SellerVerificationUtil;
import com.vendavaultecommerceproject.utils.UserModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@Service
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;
    private VideoAttachmentService videoAttachmentService;
    @Value("${baseUrl}")
    private String baseUrl;

    public VideoServiceImpl(VideoRepository videoRepository, VideoAttachmentService videoAttachmentService) {
        this.videoRepository = videoRepository;
        this.videoAttachmentService = videoAttachmentService;
    }

    @Override
    public ServerResponse uploadVideo(MultipartFile file, VideoDto videoDto, HttpServletRequest request) {
        VideoAttachmentEntity videoAttachment = videoAttachmentService.saveVideoAttachment(file);

        if (Objects.isNull(SellerVerificationUtil.isSellerValid((videoDto.getSellerEmail())))) {
            return new ServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ApiResponse(406, "User Authentication",
                    "Seller Email Address not found",
                    null));
        }
        if (Objects.isNull(videoAttachment)) {
            return new ServerResponse(baseUrl + request.getRequestURI(), "NOT OK", new ApiResponse(406, "Video Upload Information",
                    "Invalid file format, please it's only pdf, docx and jpeg file format that's accepted",
                    null));
        }
        String videoUrl = "video/" + videoAttachment.getId();
        SellerEntity seller = SellerVerificationUtil.isSellerValid(videoDto.getSellerEmail());
        VideoEntity video = VideoEntity.builder()
                .videoUrl(videoUrl)
                .title(videoDto.getTitle())
                .seller(seller)
                .build();
        videoRepository.save(video);

        return new ServerResponse(baseUrl + request.getRequestURI(), "OK", new ApiResponse(201, "Video Upload Information",
                "Video Uploaded Successfully",
                null));
    }
}
