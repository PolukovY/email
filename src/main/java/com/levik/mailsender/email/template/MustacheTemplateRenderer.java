package com.levik.mailsender.email.template;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Component
public class MustacheTemplateRenderer implements TemplateRenderer {

    private static final Logger LOG = LoggerFactory.getLogger(MustacheTemplateRenderer.class.getSimpleName());

    private final MustacheFactory mustacheFactory;

    @Autowired
    public MustacheTemplateRenderer(final MustacheFactory mustacheFactory) {
        this.mustacheFactory = mustacheFactory;
    }

    @Override
    public String render(final String mustacheTemplate, final Map<String, Object> models) {
        final Mustache mustache = mustacheFactory.compile(new StringReader(mustacheTemplate), null);
        StringWriter writer = null;
        try {
            writer = new StringWriter();
            mustache.execute(writer, models);
            return writer.toString();
        } finally {
            close(writer);
        }
    }

    private void close(StringWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException exe) {
                LOG.warn("Can't close the writer message {}", exe.getMessage());
            }
        }
    }


}
