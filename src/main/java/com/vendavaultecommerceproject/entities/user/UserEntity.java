package com.vendavaultecommerceproject.entities.user;
import ch.qos.logback.core.testUtil.RandomUtil;
import com.vendavaultecommerceproject.entities.password.ForgotPasswordEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String accountStatus;
    private boolean isVerified;
    private String phoneNumber;
    private LocalDate createdDate;

    private String identificationUrl;

    @OneToOne(mappedBy = "user")
    private ForgotPasswordEntity passwordEntity;
}
