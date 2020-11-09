package com.levik.mailsender.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.levik.mailsender.email.kafka.producer.EmailProducer;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class SimpleEmailServiceTest {

    private static final String LOCALHOST = "localhost";
    private static final String PROTOCOL_SMTP = "smtp";
    private static final int PORT = 3025;

    @Autowired
    private EmailService emailService;

    @MockBean
    private EmailProducer emailProducer;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        ServerSetup setup = new ServerSetup(PORT,LOCALHOST,PROTOCOL_SMTP);
        greenMail = new GreenMail(setup);
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    public void shouldSendSingleMail() throws MessagingException, IOException {
        //given
        String from = "no-reply@test.com";
        String to = "info@test.com";
        String subject = "Test subject";
        String text = "Some text";

        //when
        emailService.send(from, to, subject, text);

        //then
        assertThat(greenMail.waitForIncomingEmail(5000, 1)).isTrue();
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        assertThat(receivedMessages.length).isEqualTo(1);
        MimeMessage current = receivedMessages[0];

        assertThat(current.getSubject()).isEqualTo(subject);
        assertThat(IOUtils.toString(current.getInputStream()).contains(text)).isTrue();
        assertThat(current.getFrom()[0].toString()).isEqualTo(from);
        assertThat(current.getAllRecipients()[0].toString()).isEqualTo(to);
    }

}