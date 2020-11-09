package com.levik.mailsender.email;

import com.levik.mailsender.email.model.EmailModel;
import com.levik.mailsender.email.template.TemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailFacadeTest {

    private EmailService emailService;
    private TemplateService templateService;

    private EmailFacade testInstance;

    @BeforeEach
    void setUp() {
        templateService = mock(TemplateService.class);
        emailService = mock(EmailService.class);
        testInstance = new EmailFacade(emailService, templateService);
    }

    @Test
    void shouldPreparedTemplateAndPerformSend() {
        //given
        String text = "Some text";
        EmailModel email = mock(EmailModel.class);
        when(templateService.preparedTemplateContent(email.getTemplateContext())).thenReturn(text);

        //when
        testInstance.send(email);

        //then
        verify(templateService).preparedTemplateContent(email.getTemplateContext());
        verify(emailService).send(email.getFrom(), email.getTo(), email.getSubject(), text);
    }
}