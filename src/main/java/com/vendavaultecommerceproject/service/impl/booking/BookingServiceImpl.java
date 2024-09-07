package com.vendavaultecommerceproject.service.impl.booking;


import com.vendavaultecommerceproject.dto.booking.BookingDto;
import com.vendavaultecommerceproject.entities.booking.Booking;
import com.vendavaultecommerceproject.exception.exeception.DataNotAcceptableException;
import com.vendavaultecommerceproject.exception.exeception.DataNotFoundException;
import com.vendavaultecommerceproject.repository.booking.BookingRepository;
import com.vendavaultecommerceproject.response.booking.BookingResponse;
import com.vendavaultecommerceproject.response.booking.BookingUpdateResponse;
import com.vendavaultecommerceproject.service.main.booking.BookingService;
import com.vendavaultecommerceproject.util.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    @Override
    public BookingUpdateResponse updateBooking(Long bookingId, BookingDto bookingDto) throws DataNotFoundException, DataNotAcceptableException {

        Booking queriedBooking = bookingRepository.findById(bookingId).get();
        if (Objects.isNull(queriedBooking)){
            throw new DataNotFoundException("There is no booking with the provided Id");
        }
        if (bookingDto.getAppointmentDate().isAfter(LocalDate.now())){
            throw new DataNotAcceptableException("Invalid date provided");
        }
        if (Objects.nonNull(bookingDto.getStatus())){
            queriedBooking.setStatus(bookingDto.getStatus());
        }
        if (Objects.nonNull(bookingDto.getNote())){
            queriedBooking.setNote(bookingDto.getNote());
        }
        if (Objects.nonNull(bookingDto.getAppointmentDate())){
            queriedBooking.setAppointmentDate(bookingDto.getAppointmentDate());
        }
        if (Objects.nonNull(bookingDto.getAppointmentTime())){
            queriedBooking.setAppointmentTime(bookingDto.getAppointmentTime());
        }
        if (Objects.nonNull(bookingDto.getPatientId())){
            queriedBooking.setPatientId(bookingDto.getPatientId());
        }
        if (Objects.nonNull(bookingDto.getDoctorId())){
            queriedBooking.setDoctorId(bookingDto.getDoctorId());
        }
        queriedBooking.setUpdatedAt(LocalDateTime.now());
        bookingRepository.save(queriedBooking);
        return new BookingUpdateResponse("Ok",new BookingResponse(200,"Booking update","Booking updated Successfully",queriedBooking));
    }
}
