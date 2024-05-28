package com.vendavaultecommerceproject.response.user;


import com.vendavaultecommerceproject.model.user.Usermodel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse {
    private int code;
    private String title;
    private String message;
    private Usermodel data;
}
