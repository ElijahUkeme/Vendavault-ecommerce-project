package com.vendavaultecommerceproject.cotroller.video;


import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.dto.video.AdminApproveOrRejectVideoDto;
import com.vendavaultecommerceproject.dto.video.VideoDto;
import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.response.video.VideoServerListResponse;
import com.vendavaultecommerceproject.response.video.VideoServerResponse;
import com.vendavaultecommerceproject.service.main.video.VideoAttachmentService;
import com.vendavaultecommerceproject.service.main.video.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class VideoController {

    private final VideoAttachmentService videoAttachmentService;
    private final VideoService videoService;

    public VideoController(VideoAttachmentService videoAttachmentService, VideoService videoService) {
        this.videoAttachmentService = videoAttachmentService;
        this.videoService = videoService;
    }

    @PostMapping("/video/upload")
    public ResponseEntity<CustomPaymentResponse> uploadVideo(@RequestParam("videoFile") MultipartFile file, @RequestPart("video") VideoDto videoDto, HttpServletRequest request) throws DataNotAcceptableException, IOException {
        return videoService.uploadVideo(file, videoDto, request);
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<Resource> downloadProductImage(@PathVariable("videoId") String videoId) throws Exception {
        VideoAttachmentEntity videoAttachment = videoAttachmentService.getVideoAttachment(videoId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(videoAttachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "video; fileName=\"" + videoAttachment.getFileName()
                        + "\"")
                .body(new ByteArrayResource(videoAttachment.getData()));
    }


    @PostMapping("/video/admin/approve")
    public VideoServerResponse adminApproveVideo(@RequestBody AdminApproveOrRejectVideoDto approveOrRejectVideoDto,HttpServletRequest request) throws DataNotFoundException {
        return videoService.approveAVideo(approveOrRejectVideoDto,request);
    }
    @GetMapping("/video/get/{videoId}")
    public VideoServerResponse getVideo(@RequestParam("videoId")Long videoId,HttpServletRequest request) throws DataNotFoundException {
        return videoService.getAVideo(videoId,request);
    }

    @PostMapping("/video/get/all/for/seller")
    public VideoServerListResponse getAllVideosForUser(@RequestBody RetrieveUserDto retrieveUserDto, HttpServletRequest request) throws DataNotFoundException {
        return videoService.getAllVideoForSeller(retrieveUserDto,request);
    }
    @GetMapping("/video/get/all")
    public VideoServerListResponse getAllVideos(HttpServletRequest request) {
        return videoService.getAllVideos(request);
    }
    @GetMapping("/video/get/all/approved")
    public VideoServerListResponse getAllApprovedVideos(HttpServletRequest request) {
        return videoService.getAllApprovedVideo(request);
    }

}
