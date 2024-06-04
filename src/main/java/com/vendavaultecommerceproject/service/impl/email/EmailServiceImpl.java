package com.vendavaultecommerceproject.service.impl.email;

import com.vendavaultecommerceproject.model.email.EmailDetails;
import com.vendavaultecommerceproject.security.dto.EmailDto;
import com.vendavaultecommerceproject.service.main.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.core.JsonEncoding.UTF8;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessageBody());
            simpleMailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(simpleMailMessage);
            System.out.println("Email sent successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendConfirmationEmail(EmailDto emailDto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                UTF8.name()
        );
        Map<String,Object> properties = new HashMap<>();
        properties.put("username",emailDto.getUsername());
        properties.put("confirmation_url",emailDto.getConfirmationUrl());
        properties.put("activation_code",emailDto.getActivationCode());

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(senderEmail);
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());

        String template = springTemplateEngine.process(emailDto.getTemplateName(),context);
        helper.setText(template,true);
        javaMailSender.send(mimeMessage);
    }
}
