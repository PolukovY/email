package com.levik.mailsender.email.template.model;

import java.util.Map;

public class TemplateContext {
    private String identity;
    private String version;
    private String fileName;
    private Map<String, Object> data;

    public TemplateContext() {
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getIdentity() {
        return identity;
    }

    public String getVersion() {
        return version;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TemplateContext{" +
                "identity='" + identity + '\'' +
                ", version='" + version + '\'' +
                ", fileName='" + fileName + '\'' +
                ", data=" + data +
                '}';
    }
}
