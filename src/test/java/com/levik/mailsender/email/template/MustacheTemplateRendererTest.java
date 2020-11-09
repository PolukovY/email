package com.levik.mailsender.email.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MustacheTemplateRendererTest {

    private static final String TODO_EXPECTED_TEXT = "<h2>Some Title</h2>\n" +
            "<small>Created on 12/01/2020 12:20:30</small>\n" +
            "<p>Some text</p>";

    private TemplateRenderer templateRenderer;

    @BeforeEach
    void setUp() {
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        templateRenderer = new MustacheTemplateRenderer(mustacheFactory);
    }

    @Test
    void shouldRenderTodoTemplate() {
        //given
        InputStream inputStream = MustacheTemplateRendererTest.class
                .getClassLoader()
                .getResourceAsStream("todo.mustache");

        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Map<String, Object> params = createParams();

        //when
        String actual = templateRenderer.render(content, params);

        //then
        assertThat(actual).isEqualTo(TODO_EXPECTED_TEXT);
    }

    private Map<String, Object> createParams() {
        final Map<String, Object> params = new HashMap<>();

        params.put("title", "Some Title");
        params.put("createdOn", "12/01/2020 12:20:30");
        params.put("text", "Some text");


        return params;
    }

}