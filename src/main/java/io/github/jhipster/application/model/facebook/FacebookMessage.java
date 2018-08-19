package io.github.jhipster.application.model.facebook;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FacebookMessage {

    private String mid;
    private String seq;
    private String text;
    private List<FacebookAttachment> attachments = new ArrayList<>();

}
