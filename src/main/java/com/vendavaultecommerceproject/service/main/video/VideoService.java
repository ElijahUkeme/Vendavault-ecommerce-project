package com.vendavaultecommerceproject.service.main.video;

import com.vendavaultecommerceproject.dto.video.VideoDto;
import com.vendavaultecommerceproject.response.user.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    public ServerResponse uploadVideo(MultipartFile file, VideoDto videoDto, HttpServletRequest request);
}
