package io.github.jhipster.application.web.websocket.dto;

import lombok.Data;

@Data
public class FacebookRequest {

    private String messaging_type = "RESPONSE";
    private FacebookRecipient recipient = new FacebookRecipient();
    private FacebookMessage message = new FacebookMessage();

    @Data
    public class FacebookRecipient {
        private String id;
    }

    @Data
    public class FacebookMessage {
        private String text;
    }
}
