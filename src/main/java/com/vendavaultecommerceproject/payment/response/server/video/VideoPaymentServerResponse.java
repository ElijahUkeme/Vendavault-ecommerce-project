package com.vendavaultecommerceproject.payment.response.server.video;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPaymentServerResponse {
    private String terminus;
    private String status;
    private VideoPaymentResponse response;
}
