package com.vendavaultecommerceproject.service.main.email;

import com.vendavaultecommerceproject.model.email.EmailDetails;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmailAlert(EmailDetails emailDetails);
}
