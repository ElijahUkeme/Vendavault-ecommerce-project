package com.vendavaultecommerceproject.response.video;


import com.vendavaultecommerceproject.model.product.ProductModel;
import com.vendavaultecommerceproject.model.video.VideoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoListResponse {

    private int code;
    private String title;
    private String message;
    List<VideoModel> videos;
}
