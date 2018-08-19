package io.github.jhipster.application.model.line;

import lombok.Data;

@Data
public class LineEvent {
    private String replyToken;
    private String type;
    private String timestamp;
    private LineSource source;
    private LineMessage message;

}
