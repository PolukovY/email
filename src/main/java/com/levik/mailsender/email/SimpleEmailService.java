package com.levik.mailsender.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SimpleEmailService implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleEmailService.class.getSimpleName());
    public static final String UTF_8 = "UTF-8";

    private final JavaMailSender emailSender;

    @Autowired
    public SimpleEmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(String from, String to, String subject, String text) {
        MimeMessage message = sendMimeMessageHelper(from, to, subject, text);
        if (message != null) {
            emailSender.send(message);
            LOG.info("Email sent to {} successfully", to);
        }
    }

    private MimeMessage sendMimeMessageHelper(String from, String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            return message;
        } catch (MessagingException exe) {
            LOG.error("Can't send message to {} message {}", to, exe.getMessage(), exe);
            return null;
        }
    }
}
