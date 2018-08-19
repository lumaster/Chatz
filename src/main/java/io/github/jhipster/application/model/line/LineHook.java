package io.github.jhipster.application.model.line;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineHook {

    private List<LineEvent> events = new ArrayList<>();


}
