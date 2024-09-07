package com.vendavaultecommerceproject.response.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateResponse {
    private String status;
    private BookingResponse response;
}
