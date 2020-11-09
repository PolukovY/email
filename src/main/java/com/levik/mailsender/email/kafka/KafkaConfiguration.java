package com.levik.mailsender.email.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levik.mailsender.email.EmailFacade;
import com.levik.mailsender.email.kafka.listener.EmailConsumer;
import com.levik.mailsender.email.kafka.producer.EmailProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfiguration {

    @Bean
    public EmailConsumer emailConsumer(EmailFacade emailFacade, ObjectMapper objectMapper) {
        return new EmailConsumer(emailFacade, objectMapper);
    }

    @Bean
    public EmailProducer emailProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${kafka.topic}") String topic,
            ObjectMapper objectMapper) {
        return new EmailProducer(kafkaTemplate, topic, objectMapper);
    }
}
