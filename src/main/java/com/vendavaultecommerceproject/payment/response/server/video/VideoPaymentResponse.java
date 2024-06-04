package com.vendavaultecommerceproject.payment.response.server.video;


import com.vendavaultecommerceproject.payment.model.video.VideoUploadPaymentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPaymentResponse {
    private int code;
    private String title;
    private String message;
    private List<VideoUploadPaymentModel> data;
}
