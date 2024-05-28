package com.vendavaultecommerceproject.payment.response.server.user;


import com.vendavaultecommerceproject.payment.response.server.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServerResponse {

    private String terminus;
    private String status;
    private UserResponse response;
}
