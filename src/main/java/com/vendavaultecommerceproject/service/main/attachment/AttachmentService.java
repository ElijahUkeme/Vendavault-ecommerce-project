package com.vendavaultecommerceproject.service.main.attachment;

import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    public AttachmentEntity saveAttachment(MultipartFile file) throws Exception;
    public AttachmentEntity getAttachment(String attachmentId) throws Exception;
}
