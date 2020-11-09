package com.levik.mailsender.email.template;

import java.util.Map;

public interface TemplateRenderer {

    String render(String mustacheTemplate, Map<String, Object> model);
}
