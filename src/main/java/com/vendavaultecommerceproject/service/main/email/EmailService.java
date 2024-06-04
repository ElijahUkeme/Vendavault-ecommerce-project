package com.vendavaultecommerceproject.service.main.email;

import com.vendavaultecommerceproject.model.email.EmailDetails;
import com.vendavaultecommerceproject.security.dto.EmailDto;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmailAlert(EmailDetails emailDetails);

    @Async
    void sendConfirmationEmail(EmailDto emailDto) throws MessagingException;
}
