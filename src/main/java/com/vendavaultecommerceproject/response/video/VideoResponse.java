package com.vendavaultecommerceproject.response.video;

import com.vendavaultecommerceproject.model.video.VideoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {

    private int code;
    private String title;
    private String message;
    private VideoModel data;
}
