package com.levik.mailsender.email;

import com.levik.mailsender.email.model.EmailModel;
import com.levik.mailsender.email.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailFacade {

    private final EmailService emailService;
    private final TemplateService templateService;

    @Autowired
    public EmailFacade(EmailService emailService, TemplateService templateService) {
        this.emailService = emailService;
        this.templateService = templateService;
    }

    public void send(EmailModel email) {
        String text = templateService.preparedTemplateContent(email.getTemplateContext());
        emailService.send(email.getFrom(), email.getTo(), email.getSubject(), text);
    }
}
