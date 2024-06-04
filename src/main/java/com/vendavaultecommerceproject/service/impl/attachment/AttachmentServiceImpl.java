package com.vendavaultecommerceproject.service.impl.attachment;

import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import com.vendavaultecommerceproject.repository.attachment.AttachmentRepository;
import com.vendavaultecommerceproject.service.main.attachment.AttachmentService;
import com.vendavaultecommerceproject.util.constants.AppStrings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    private  AttachmentRepository attachmentRepository;
    AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }
    @Override
    public AttachmentEntity saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {

            if (!(StringUtils.endsWithIgnoreCase(fileName,"pdf")||StringUtils.endsWithIgnoreCase(fileName,"jpg"))){
                throw new Exception(AppStrings.attachmentMessage);
            }
            if (fileName.contains("..")){
                throw new Exception(AppStrings.invalidFieldMessage);
            }else {
                AttachmentEntity attachment = new AttachmentEntity(file.getContentType(),fileName,file.getBytes());
                return attachmentRepository.save(attachment);
            }

        }catch (Exception e){
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public AttachmentEntity getAttachment(String attachmentId) throws Exception {
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(()->new Exception(AppStrings.fileIdNotFoundMessage));
    }
}
