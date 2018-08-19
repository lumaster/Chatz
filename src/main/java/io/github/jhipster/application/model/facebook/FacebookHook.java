package io.github.jhipster.application.model.facebook;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FacebookHook {

    private String object;
    private List<Entry> entry = new ArrayList<>();

}
