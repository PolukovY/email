package com.levik.mailsender.email.model;

import com.levik.mailsender.email.template.model.TemplateContext;

public class EmailModel {
    private String to;
    private String from;
    private String subject;
    private TemplateContext templateContext;

    public EmailModel() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TemplateContext getTemplateContext() {
        return templateContext;
    }

    public void setTemplateContext(TemplateContext templateContext) {
        this.templateContext = templateContext;
    }

    @Override
    public String toString() {
        return "EmailModel{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", templateContext=" + templateContext +
                '}';
    }
}
