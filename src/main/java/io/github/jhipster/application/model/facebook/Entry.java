package io.github.jhipster.application.model.facebook;

import lombok.Data;

import java.util.List;

@Data
public class Entry {

    private String id;
    private String time;
    private List<Messaging> messaging;

}
