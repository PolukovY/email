package com.levik.mailsender.email.template.utils;

import com.levik.mailsender.email.template.exception.TemplateNotFoundException;
import com.levik.mailsender.email.template.model.TemplateContext;
import io.micrometer.core.instrument.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TemplateLoaderUtils {

    private static final String TEMPLATE_NAME = "templates";
    private static final String PATH = TEMPLATE_NAME + File.separator +
            "%s" + File.separator +
            "%s" + File.separator +
            "%s" + File.separator;


    public static String getContent(TemplateContext templateContext) {
        String templatePath = String.format(PATH,
                templateContext.getIdentity(),
                templateContext.getVersion(),
                templateContext.getFileName()
        );

        try {
            try (InputStream inputStream = TemplateLoaderUtils.class
                    .getClassLoader()
                    .getResourceAsStream(templatePath)) {
                return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            }
        } catch (IOException exe) {
            String message = String.format("Can't load the templatePath %s message %s", templatePath, exe.getMessage());
            throw new TemplateNotFoundException(message, exe, templatePath);
        }
    }
}
