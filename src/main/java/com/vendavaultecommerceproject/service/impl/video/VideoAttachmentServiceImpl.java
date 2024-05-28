package com.vendavaultecommerceproject.service.impl.video;


import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import com.vendavaultecommerceproject.repository.video.VideoAttachmentRepository;
import com.vendavaultecommerceproject.service.main.video.VideoAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
@Slf4j
public class VideoAttachmentServiceImpl implements VideoAttachmentService {

    private VideoAttachmentRepository videoAttachmentRepository;

    public VideoAttachmentServiceImpl(VideoAttachmentRepository videoAttachmentRepository) {
        this.videoAttachmentRepository = videoAttachmentRepository;
    }

    @Override
    public VideoAttachmentEntity saveVideoAttachment(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {

            if (fileName.contains("..")) {
                throw new Exception("File Name Contains invalid character");
            } else {
                VideoAttachmentEntity attachment = new VideoAttachmentEntity(file.getContentType(), fileName, file.getBytes());
                return videoAttachmentRepository.save(attachment);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public VideoAttachmentEntity getVideoAttachment(String videoId) throws Exception {
        return videoAttachmentRepository.findById(videoId)
                .orElseThrow(()->new Exception("Video Id not found"));
    }
}
