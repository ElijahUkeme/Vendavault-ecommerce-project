package com.vendavaultecommerceproject.response.user;


import com.vendavaultecommerceproject.response.user.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServerResponse {
    private String terminus;
    private String status;
    private ApiResponse response;
}
