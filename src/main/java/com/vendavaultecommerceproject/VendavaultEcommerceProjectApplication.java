package com.vendavaultecommerceproject;

import com.vendavaultecommerceproject.entities.booking.Booking;
import com.vendavaultecommerceproject.repository.booking.BookingRepository;
import com.vendavaultecommerceproject.security.entity.Role;
import com.vendavaultecommerceproject.security.repository.AppUserRepository;
import com.vendavaultecommerceproject.security.repository.RoleRepository;
import com.vendavaultecommerceproject.util.enums.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class VendavaultEcommerceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendavaultEcommerceProjectApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(RoleRepository repository) {
//		return args -> {
//			if (repository.findByName("MANAGER").isEmpty()) {
//				repository.save(Role.builder()
//						.name("MANAGER")
//						.build());
//			}
//		};
//
//
//	}

	CommandLineRunner runners(BookingRepository bookingRepository) {
		return args -> {
			Booking booking = Booking.builder()
					.appointmentDate(LocalDate.parse("2024-08-23"))
					.appointmentTime(LocalDateTime.now())
					.status(Status.PENDING)
					.createdAt(LocalDateTime.now())
					.doctorId(5L)
					.patientId(4L)
					.totalPrice(BigDecimal.valueOf(200))
					.paymentId(1L)
					.note("Testing booking update")
					.build();
			bookingRepository.save(booking);
		};

	}
}
