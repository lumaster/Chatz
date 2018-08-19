package io.github.jhipster.application.web.websocket.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineRequest {
    private String to;
    private List<LineMessage> messages = new ArrayList<>();

    public LineMessage createLineMessage(){
        return new LineMessage();
    }
    @Data
    public class LineMessage {
        private String type = "text";
        private String text;
    }
}


