package com.levik.mailsender.email.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levik.mailsender.app.annotation.IncomingStatisticMarker;
import com.levik.mailsender.email.EmailFacade;
import com.levik.mailsender.email.model.EmailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;
import java.util.Optional;

public class EmailConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(EmailConsumer.class.getSimpleName());

    private final EmailFacade emailFacade;
    private final ObjectMapper objectMapper;

    public EmailConsumer(EmailFacade emailFacade, ObjectMapper objectMapper) {
        this.emailFacade = emailFacade;
        this.objectMapper = objectMapper;
    }

    @IncomingStatisticMarker(name = "message")
    @KafkaListener(topics = "${kafka.topic}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info(
                "Received from [topic={}, partitions={}, offsets={}] message {}",
                topics.get(0), partitions.get(0), offsets.get(0), message
        );


        getEmail(message).ifPresent(emailFacade::send);
    }


    private Optional<EmailModel> getEmail(String message) {
        try {
            return Optional.of(objectMapper.readValue(message, EmailModel.class));
        } catch (Exception exe) {
            LOG.error("Can't convert message {} error {}", message, exe.getMessage(), exe);
            return Optional.empty();
        }
    }
}
