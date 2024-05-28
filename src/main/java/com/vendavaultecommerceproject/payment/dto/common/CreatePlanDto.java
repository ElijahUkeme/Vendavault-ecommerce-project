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
public class CreatePlanDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("interval")
    private String interval;

    private Integer amount;
}