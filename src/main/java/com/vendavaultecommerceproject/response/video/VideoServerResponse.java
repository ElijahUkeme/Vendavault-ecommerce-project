package com.vendavaultecommerceproject.response.video;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoServerResponse {

    private String terminus;
    private String status;
    private VideoResponse response;
}
