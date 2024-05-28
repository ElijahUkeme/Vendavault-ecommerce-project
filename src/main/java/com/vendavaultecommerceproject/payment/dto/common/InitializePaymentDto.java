package com.vendavaultecommerceproject.payment.dto.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitializePaymentDto {


    @JsonProperty("amount")
    private double amount;


    @JsonProperty("email")
    private String email;


    @JsonProperty("currency")
    private String currency;


    @JsonProperty("plan")
    private String plan;

//    @NotNull(message = "Channels cannot be null")
//    @JsonProperty("channels")
//    private String[] channels;
}
