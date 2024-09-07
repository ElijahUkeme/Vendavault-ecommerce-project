package com.vendavaultecommerceproject.response.booking;


import com.vendavaultecommerceproject.entities.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private int code;
    private String title;
    private String message;
    private Booking data;
}
