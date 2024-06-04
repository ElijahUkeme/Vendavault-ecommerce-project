package com.vendavaultecommerceproject.dto.video;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminApproveOrRejectVideoDto {

    private Long videoId;
    private String status;
}
