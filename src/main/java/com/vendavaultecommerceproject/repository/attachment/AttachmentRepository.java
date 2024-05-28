package com.vendavaultecommerceproject.repository.attachment;

import com.vendavaultecommerceproject.entities.attachment.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity,String> {
}
