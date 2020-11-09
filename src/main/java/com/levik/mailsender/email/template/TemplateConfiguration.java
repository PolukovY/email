package com.levik.mailsender.email.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateConfiguration {

    @Bean
    public MustacheFactory mustacheFactory() {
        return new DefaultMustacheFactory();
    }
}
