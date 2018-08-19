package io.github.jhipster.application.model.line;


import lombok.Data;

@Data
public class LineMessage {

    private String id;
    private String type;
    private String text;
    private String stickerId;
    private String packageId;
}
