package com.vendavaultecommerceproject.service.main.booking;

import com.vendavaultecommerceproject.dto.booking.BookingDto;
import com.vendavaultecommerceproject.entities.booking.Booking;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.booking.BookingUpdateResponse;

public interface BookingService {

    public BookingUpdateResponse updateBooking(Long bookingId, BookingDto bookingDto) throws DataNotFoundException, DataNotAcceptableException;
}
