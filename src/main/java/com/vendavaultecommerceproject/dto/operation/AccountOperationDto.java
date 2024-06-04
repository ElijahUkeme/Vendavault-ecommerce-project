package com.vendavaultecommerceproject.dto.operation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperationDto {

    private String  email;
    private String reason;
}
