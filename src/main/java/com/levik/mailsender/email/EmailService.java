package com.levik.mailsender.email;

public interface EmailService {

    void send(String from, String to, String subject, String text);
}
