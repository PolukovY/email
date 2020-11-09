package com.levik.mailsender;

import org.junit.ClassRule;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;

public abstract class BaseIntegrationTests {

    private static final String TOPIC_NAME = "test-default";

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);


}
