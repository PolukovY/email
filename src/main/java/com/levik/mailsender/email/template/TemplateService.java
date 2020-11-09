package com.levik.mailsender.email.template;

import com.levik.mailsender.email.template.model.TemplateContext;
import com.levik.mailsender.email.template.utils.TemplateLoaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private final TemplateRenderer templateRenderer;

    @Autowired
    public TemplateService(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    public String preparedTemplateContent(TemplateContext templateContext) {
        String content = TemplateLoaderUtils.getContent(templateContext);
        return templateRenderer.render(content, templateContext.getData());

    }
}
