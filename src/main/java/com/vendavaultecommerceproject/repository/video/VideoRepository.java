package com.vendavaultecommerceproject.repository.video;

import com.vendavaultecommerceproject.entities.video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Long> {
}
