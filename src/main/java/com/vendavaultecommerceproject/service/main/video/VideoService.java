package com.vendavaultecommerceproject.service.main.video;

import com.vendavaultecommerceproject.dto.user.RetrieveUserDto;
import com.vendavaultecommerceproject.dto.video.AdminApproveOrRejectVideoDto;
import com.vendavaultecommerceproject.dto.video.VideoDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import com.vendavaultecommerceproject.response.video.VideoServerListResponse;
import com.vendavaultecommerceproject.response.video.VideoServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    public ResponseEntity<CustomPaymentResponse> uploadVideo(MultipartFile file, VideoDto videoDto, HttpServletRequest request) throws DataNotAcceptableException, IOException;
    public VideoServerResponse approveAVideo(AdminApproveOrRejectVideoDto approveOrRejectVideoDto,HttpServletRequest request) throws DataNotFoundException;
    public VideoServerResponse getAVideo(Long videoId,HttpServletRequest request) throws DataNotFoundException;
    public VideoServerListResponse getAllVideoForSeller(RetrieveUserDto retrieveUserDto,HttpServletRequest request) throws DataNotFoundException;
    public VideoServerListResponse getAllVideos(HttpServletRequest request);
    public VideoServerListResponse getAllApprovedVideo(HttpServletRequest request);
}
