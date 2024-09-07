package com.vendavaultecommerceproject.repository.booking;

import com.vendavaultecommerceproject.entities.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
}
