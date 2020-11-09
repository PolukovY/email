package com.levik.mailsender.email.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levik.mailsender.email.model.EmailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class EmailProducer {

    private static final Logger LOG = LoggerFactory.getLogger(EmailProducer.class.getSimpleName());

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;
    private final ObjectMapper objectMapper;

    public EmailProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            String topic,
            ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.objectMapper = objectMapper;
    }

    public void send(EmailModel email) {
        String message = convertToString(email);
        if (message == null) {
            LOG.warn("Email {} will be skipped.", email);
            return;
        }
        this.kafkaTemplate.send(topic, message);
        LOG.info("Send message {} to topic {}", message, topic);
    }

    private String convertToString(EmailModel email) {
        try {
            return objectMapper.writeValueAsString(email);
        } catch (Exception exe) {
            LOG.warn("Can't convert email {} error {}", email, exe.getMessage());
            return null;
        }
    }

}
