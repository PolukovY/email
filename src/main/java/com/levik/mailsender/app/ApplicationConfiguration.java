package com.levik.mailsender.app;

import com.levik.mailsender.app.service.IncomingStatisticService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public IncomingStatisticService incomingStatisticService() {
        return new IncomingStatisticService(new ConcurrentHashMap<>());
    }
}
