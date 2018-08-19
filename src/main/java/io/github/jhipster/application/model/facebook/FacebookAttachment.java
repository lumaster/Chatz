package io.github.jhipster.application.model.facebook;

import lombok.Data;

@Data
public class FacebookAttachment {

    private String type;
    private FacebookPayload payload;

    @Data
    public class FacebookPayload {
        private String url;
    }
}
