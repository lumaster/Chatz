package io.github.jhipster.application.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Primary
@Configuration
@ConfigurationProperties("api")
public class APIProperties {

    private Map<String, Map<String, String>> facebook = new HashMap<>();
    private Map<String, Map<String, String>> line = new HashMap<>();

    public Map<String, String> getFacebookURLs() {
        return facebook.get("url");
    }

    public Map<String, String> getLineURLs() {
        return line.get("url");
    }

    public Map<String, String> getFacebookHeaders() {
        return facebook.get("header");
    }

    public Map<String, String> getLineHeaders() {
        return line.get("header");
    }

    public Map<String, Map<String, String>> getFacebook() {
        return facebook;
    }

    public void setFacebook(Map<String, Map<String, String>> facebook) {
        this.facebook = facebook;
    }

    public Map<String, Map<String, String>> getLine() {
        return line;
    }

    public void setLine(Map<String, Map<String, String>> line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "APIProperties{" +
            "facebook=" + facebook +
            ", line=" + line +
            '}';
    }
}
