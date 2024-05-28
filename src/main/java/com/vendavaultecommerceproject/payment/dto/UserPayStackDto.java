package com.vendavaultecommerceproject.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPayStackDto {


    private String name;


    private String interval;


    private Integer amount;
}
