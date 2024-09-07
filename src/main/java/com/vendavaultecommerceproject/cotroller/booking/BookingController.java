package com.vendavaultecommerceproject.cotroller.booking;


import com.vendavaultecommerceproject.dto.booking.BookingDto;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.response.booking.BookingUpdateResponse;
import com.vendavaultecommerceproject.service.main.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookings/")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PutMapping("update/{bookingId}")
    public BookingUpdateResponse updateBooking(@RequestParam("bookingId")Long bookingId, @RequestBody BookingDto bookingDto) throws DataNotFoundException, DataNotAcceptableException {
        return bookingService.updateBooking(bookingId,bookingDto);
    }
}
