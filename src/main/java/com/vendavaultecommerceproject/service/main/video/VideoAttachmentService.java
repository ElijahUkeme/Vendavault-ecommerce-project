package com.vendavaultecommerceproject.service.main.video;

import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

public interface VideoAttachmentService {

    VideoAttachmentEntity saveVideoAttachment(MultipartFile file);
    VideoAttachmentEntity getVideoAttachment(String videoId) throws Exception;
}
