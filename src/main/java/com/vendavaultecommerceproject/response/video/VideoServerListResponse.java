package com.vendavaultecommerceproject.response.video;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoServerListResponse {

    private String terminus;
    private String status;
    private VideoListResponse response;
}
