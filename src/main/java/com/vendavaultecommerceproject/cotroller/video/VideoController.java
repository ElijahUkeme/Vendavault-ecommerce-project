package com.vendavaultecommerceproject.cotroller.video;


import com.vendavaultecommerceproject.dto.video.VideoDto;
import com.vendavaultecommerceproject.entities.product.image.ProductImageEntity;
import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.response.user.ServerResponse;
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

@RestController
public class VideoController {

    private VideoAttachmentService videoAttachmentService;
    private VideoService videoService;

    public VideoController(VideoAttachmentService videoAttachmentService, VideoService videoService) {
        this.videoAttachmentService = videoAttachmentService;
        this.videoService = videoService;
    }


    @PostMapping("/video/upload")
    public ServerResponse uploadVideo(@RequestParam("videoFile") MultipartFile file, @RequestPart("video") VideoDto videoDto, HttpServletRequest request) {
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

}
