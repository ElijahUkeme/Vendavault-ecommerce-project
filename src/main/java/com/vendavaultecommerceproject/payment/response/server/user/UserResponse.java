package com.vendavaultecommerceproject.payment.response.server.user;


import com.vendavaultecommerceproject.payment.model.user.UserPaymentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private int code;
    private String title;
    private String message;
    private List<UserPaymentModel> data;
}
