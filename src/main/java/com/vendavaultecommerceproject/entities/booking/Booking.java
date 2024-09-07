package com.vendavaultecommerceproject.entities.booking;


import com.vendavaultecommerceproject.util.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
@Builder
public class Booking {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //This should be mapped to the doctor entity
    private Long doctorId;
    //This should be mapped to the user entity
    private Long patientId;
    private LocalDate appointmentDate;
    private LocalDateTime appointmentTime;
    private BigDecimal totalPrice;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //This should be mapped to the payment entity
    private Long paymentId;
    private String note;
}
