package com.levik.mailsender.email.template.exception;

public class TemplateNotFoundException extends RuntimeException {

    private final String templatePath;

    public TemplateNotFoundException(String message, Throwable cause, String templatePath) {
        super(message, cause);
        this.templatePath = templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}
