package com.vendavaultecommerceproject.model.user;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Usermodel {

    private Long id;
    private String name;
    private String username;
    private String email;
    private boolean isUserVerified;
    private String phoneNumber;
    private LocalDate createdDate;

    private String identificationUrl;
}
