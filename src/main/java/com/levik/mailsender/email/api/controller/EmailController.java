package com.levik.mailsender.email.api.controller;

import com.levik.mailsender.email.model.EmailModel;
import com.levik.mailsender.email.kafka.producer.EmailProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class EmailController {

    private final EmailProducer emailProducer;

    @Autowired
    public EmailController(EmailProducer emailProducer) {
        this.emailProducer = emailProducer;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public void send(@RequestBody EmailModel email) {
        emailProducer.send(email);
    }
}
