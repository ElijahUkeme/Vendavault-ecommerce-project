package com.vendavaultecommerceproject.service.main.video;

import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoAttachmentService {

    VideoAttachmentEntity saveVideoAttachment(MultipartFile file) throws DataNotAcceptableException, IOException;
    VideoAttachmentEntity getVideoAttachment(String videoId) throws Exception;
}
