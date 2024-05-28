package com.vendavaultecommerceproject.repository.video;

import com.vendavaultecommerceproject.entities.video.VideoAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoAttachmentRepository extends JpaRepository<VideoAttachmentEntity,String> {
}
