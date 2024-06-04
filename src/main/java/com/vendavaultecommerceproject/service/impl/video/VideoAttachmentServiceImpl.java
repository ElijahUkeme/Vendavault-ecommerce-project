package com.vendavaultecommerceproject.service.impl.video;


import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.repository.video.VideoAttachmentRepository;
import com.vendavaultecommerceproject.service.main.video.VideoAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class VideoAttachmentServiceImpl implements VideoAttachmentService {

    private VideoAttachmentRepository videoAttachmentRepository;

    public VideoAttachmentServiceImpl(VideoAttachmentRepository videoAttachmentRepository) {
        this.videoAttachmentRepository = videoAttachmentRepository;
    }

    @Override
    public VideoAttachmentEntity saveVideoAttachment(MultipartFile file) throws DataNotAcceptableException, IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Get length of file in bytes
        long fileSizeInBytes = file.getSize();
         // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

            if (fileName.contains("..")) {
                throw new DataNotAcceptableException("File Name Contains invalid character");
            }if (fileSizeInMB >5){
                throw new DataNotAcceptableException("Maximum video size should be 5mb");
            }
            else {
                VideoAttachmentEntity attachment = new VideoAttachmentEntity(file.getContentType(), fileName, file.getBytes());
                return videoAttachmentRepository.save(attachment);
            }

    }

    @Override
    public VideoAttachmentEntity getVideoAttachment(String videoId) throws Exception {
        return videoAttachmentRepository.findById(videoId)
                .orElseThrow(()->new DataNotFoundException("Video Id not found"));
    }
}
