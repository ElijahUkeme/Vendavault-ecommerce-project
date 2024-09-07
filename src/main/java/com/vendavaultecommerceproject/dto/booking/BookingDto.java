package com.vendavaultecommerceproject.dto.booking;

import com.vendavaultecommerceproject.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    private Long doctorId;
    private Long patientId;
    private LocalDate appointmentDate;
    private LocalDateTime appointmentTime;
    private BigDecimal totalPrice;
    private Status status;
    private String note;
}
